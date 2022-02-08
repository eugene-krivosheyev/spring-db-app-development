package com.acme.dbo.account.dao;

import com.acme.dbo.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;

@Primary
@Repository
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
public class JdbcAccountRepository implements AccountRepository {
    @Autowired DataSource dataSource;

    @Override
    public Collection<Account> findAllAccounts() throws SQLException {
        log.warn("using JdbcAccountRepository");

        try (final Connection connection = dataSource.getConnection()) {
            final Collection<Account> accounts = new ArrayList<>();

            final ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM ACCOUNT");
            while (resultSet.next()) {
                accounts.add(new Account(
                        resultSet.getInt("ID"),
                        resultSet.getInt("CLIENT_ID"),
                        resultSet.getFloat("AMOUNT"))
                );
            }

            return accounts;
        }
    }
}
