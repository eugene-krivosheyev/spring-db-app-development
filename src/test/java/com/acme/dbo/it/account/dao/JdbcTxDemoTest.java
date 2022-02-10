package com.acme.dbo.it.account.dao;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static lombok.AccessLevel.PRIVATE;

/**
 * https://docs.oracle.com/javadb/10.8.3.0/devguide/cdevconcepts15366.html
 */
@Disabled
@SpringBootTest
@ActiveProfiles("it") //TODO 'st'
@FieldDefaults(level = PRIVATE)
@Slf4j
public class JdbcTxDemoTest {
    @Autowired DataSource dataSource;

    @Test
    public void shouldReproduceDirtyReadsOfFieldBeingNotYetCommitted() throws InterruptedException {
        final int TRANSACTION_ISOLATION_LEVEL = Connection.TRANSACTION_READ_UNCOMMITTED;
        final AtomicReference<String> thread1user1updateLogin = new AtomicReference<>();
        final AtomicReference<String> thread2user1selectLogin = new AtomicReference<>();

        new Thread(() -> {
            try (
                final Connection connection = dataSource.getConnection();
                final PreparedStatement preparedStatement =
                        connection.prepareStatement("UPDATE CLIENT SET LOGIN = ? WHERE ID = 1");
            ) {

                connection.setAutoCommit(false);
                connection.setTransactionIsolation(TRANSACTION_ISOLATION_LEVEL);

                final String newLogin = UUID.randomUUID().toString();
                thread1user1updateLogin.set(newLogin);
                preparedStatement.setString(1, newLogin);
                System.out.println(">>> Thread1 executing update user#1 to " + newLogin);
                preparedStatement.executeUpdate();
                System.out.println(">>> Thread1 executed update");

                System.out.println(">>> Thread1 waiting 2 sec...");
                Thread.sleep(2_000);
                System.out.println(">>> Thread1 rolling back...");
                connection.rollback();
                System.out.println(">>> Thread1 rolled back");

            } catch (SQLException | InterruptedException e) {
                System.err.println("Thread1");
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try (
                    final Connection connection = dataSource.getConnection();
                    final PreparedStatement preparedStatement = connection.prepareStatement("SELECT LOGIN FROM CLIENT WHERE ID = 1"); //TODO 'FOR UPDATE/SHARE' https://ru.stackoverflow.com/questions/1064951/Зачем-нужен-select-for-update
            ) {

                connection.setAutoCommit(false);
                connection.setTransactionIsolation(TRANSACTION_ISOLATION_LEVEL);

                System.out.println(">>> Thread2 waiting 1 sec...");
                Thread.sleep(1_000);
                System.out.println(">>> Thread2 executing select...");
                final ResultSet clientResultSet = preparedStatement.executeQuery();
                clientResultSet.next();
                final String clientLogin = clientResultSet.getString(1);
                System.out.println(">>> Thread2 executed select user#1 login as " + clientLogin);

                thread2user1selectLogin.set(clientLogin);

            } catch (SQLException | InterruptedException e) {
                System.err.println("Thread2");
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(3_000);
        Assertions.assertEquals(thread2user1selectLogin.get(), thread1user1updateLogin.get());
        Assertions.assertFalse(thread2user1selectLogin.get().contains("@"));
    }

    @Test
    public void shouldReproduceNonRepeatableReadsOfSameRowBeingUpdated() throws InterruptedException {
        final int TRANSACTION_ISOLATION_LEVEL = Connection.TRANSACTION_READ_COMMITTED;
        final AtomicReference<String> thread1user1selectLoginFirst = new AtomicReference<>();
        final AtomicReference<String> thread1user1selectLoginSecond = new AtomicReference<>();

        new Thread(() -> {
            try (
                    final Connection connection = dataSource.getConnection();
                    final PreparedStatement preparedStatement =
                            connection.prepareStatement("SELECT LOGIN FROM CLIENT WHERE ID = 1"); //TODO 'FOR UPDATE'
            ) {

                connection.setAutoCommit(false);
                connection.setTransactionIsolation(TRANSACTION_ISOLATION_LEVEL);

                System.out.println(">>> Thread1 executing first select...");
                ResultSet clientResultSet = preparedStatement.executeQuery();
                clientResultSet.next();
                thread1user1selectLoginFirst.set(clientResultSet.getString(1));
                System.out.println(">>> Thread1 executed first select with login " + thread1user1selectLoginFirst.get());

                System.out.println(">>> Thread1 waiting 2 sec...");
                Thread.sleep(2_000);

                System.out.println(">>> Thread1 executing second select...");
                clientResultSet = preparedStatement.executeQuery();
                clientResultSet.next();
                thread1user1selectLoginSecond.set(clientResultSet.getString(1));
                System.out.println(">>> Thread1 executed second select with login " + thread1user1selectLoginSecond.get());

            } catch (SQLException | InterruptedException e) {
                System.err.println("Thread1");
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try (
                    final Connection connection = dataSource.getConnection();
                    final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE CLIENT SET LOGIN = ? WHERE ID = 1");
            ) {

                connection.setAutoCommit(false);
                connection.setTransactionIsolation(TRANSACTION_ISOLATION_LEVEL);
                System.out.println(">>> Thread2 waiting 1 sec...");
                Thread.sleep(1_000);

                final String newLogin = UUID.randomUUID().toString();
                preparedStatement.setString(1, newLogin);
                System.out.println(">>> Thread2 executing update user#1 to " + newLogin);
                preparedStatement.executeUpdate();
                System.out.println(">>> Thread2 executed update");

                System.out.println(">>> Thread2 committing...");
                connection.commit();
                System.out.println(">>> Thread2 committed");

            } catch (SQLException | InterruptedException e) {
                System.err.println("Thread2");
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(3_000);
        Assertions.assertNotEquals(thread1user1selectLoginFirst.get(), thread1user1selectLoginSecond.get());
        Assertions.assertTrue(thread1user1selectLoginFirst.get().contains("@"));
    }

    @Test
    public void shouldReproducePhantomReadsOfSameSetBeingInserted() throws InterruptedException {
        final int TRANSACTION_ISOLATION_LEVEL = Connection.TRANSACTION_REPEATABLE_READ;
        final AtomicReference<Integer> thread1user1selectLoginFirst = new AtomicReference<>();
        final AtomicReference<Integer> thread1user1selectLoginSecond = new AtomicReference<>();

        new Thread(() -> {
            try (
                    final Connection connection = dataSource.getConnection();
                    final PreparedStatement preparedStatement =
                            connection.prepareStatement("SELECT COUNT(ID) FROM CLIENT WHERE ID > 1"); //TODO 'FOR UPDATE'
            ) {

                connection.setAutoCommit(false);
                connection.setTransactionIsolation(TRANSACTION_ISOLATION_LEVEL);

                System.out.println(">>> Thread1 executing first select...");
                ResultSet clientResultSet = preparedStatement.executeQuery();
                clientResultSet.next();
                thread1user1selectLoginFirst.set(clientResultSet.getInt(1));
                System.out.println(">>> Thread1 executed first select with count " + thread1user1selectLoginFirst.get());

                System.out.println(">>> Thread1 waiting 2 sec...");
                Thread.sleep(2_000);

                System.out.println(">>> Thread1 executing second select...");
                clientResultSet = preparedStatement.executeQuery();
                clientResultSet.next();
                thread1user1selectLoginSecond.set(clientResultSet.getInt(1));
                System.out.println(">>> Thread1 executed second select with count " + thread1user1selectLoginSecond.get());

            } catch (SQLException | InterruptedException e) {
                System.err.println("Thread1");
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try (
                    final Connection connection = dataSource.getConnection();
                    final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CLIENT(LOGIN) VALUES(?)");
            ) {

                connection.setAutoCommit(false);
                connection.setTransactionIsolation(TRANSACTION_ISOLATION_LEVEL);
                System.out.println(">>> Thread2 waiting 1 sec...");
                Thread.sleep(1_000);

                final String newLogin = UUID.randomUUID().toString();
                preparedStatement.setString(1, newLogin);
                System.out.println(">>> Thread2 executing insert user with login " + newLogin);
                preparedStatement.executeUpdate();
                System.out.println(">>> Thread2 executed insert");

                System.out.println(">>> Thread2 committing...");
                connection.commit();
                System.out.println(">>> Thread2 committed");

            } catch (SQLException | InterruptedException e) {
                System.err.println("Thread2");
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(3_000);
        Assertions.assertNotEquals(thread1user1selectLoginFirst.get(), thread1user1selectLoginSecond.get());
    }
}
