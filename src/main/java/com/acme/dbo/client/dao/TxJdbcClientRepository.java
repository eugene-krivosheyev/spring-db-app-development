package com.acme.dbo.client.dao;

import com.acme.dbo.client.domain.Client;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
@Transactional // TODO NB!
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class TxJdbcClientRepository implements ClientRepository {
    @Autowired final DataSource dataSource;

    @Override
    public Long save(Client clientDto) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet keysResultSet = null;

        try {
            connection = DataSourceUtils.getConnection(dataSource); //TODO NB!
            preparedStatement = connection.prepareStatement("INSERT INTO CLIENT(LOGIN, ENABLED) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, clientDto.getLogin());
            preparedStatement.setBoolean(2, clientDto.getEnabled());
            preparedStatement.executeUpdate();

            keysResultSet = preparedStatement.getGeneratedKeys();
            keysResultSet.next();
            return keysResultSet.getLong(1);

        } finally {
            JdbcUtils.closeResultSet(keysResultSet);
            JdbcUtils.closeStatement(preparedStatement);
            DataSourceUtils.releaseConnection(connection, dataSource); //TODO NB!
        }
    }

    @Override
    public Collection<Client> findAllClients() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet clientsResultSet = null;

        try {
            connection = DataSourceUtils.getConnection(dataSource);
            preparedStatement = connection.prepareStatement("SELECT ID, LOGIN, ENABLED FROM CLIENT");
            clientsResultSet = preparedStatement.executeQuery();

            final Collection<Client> clients = new ArrayList<>();
            while (clientsResultSet.next()) {
                clients.add(new Client(
                        clientsResultSet.getLong("ID"),
                        clientsResultSet.getString("LOGIN"),
                        clientsResultSet.getBoolean("ENABLED"))
                );
            }

            return clients;

        } finally {
            JdbcUtils.closeResultSet(clientsResultSet);
            JdbcUtils.closeStatement(preparedStatement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    @Override
    public Optional<Client> findById(Long primaryKey) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet clientsResultSet = null;

        try {
                connection = DataSourceUtils.getConnection(dataSource);
                preparedStatement = connection.prepareStatement("SELECT ID, LOGIN, ENABLED FROM CLIENT WHERE ID = ?");

                preparedStatement.setLong(1, primaryKey);
                clientsResultSet = preparedStatement.executeQuery();

                if (!clientsResultSet.next()) return Optional.empty();
                return Optional.of(
                        new Client(
                                clientsResultSet.getLong("ID"),
                                clientsResultSet.getString("LOGIN"),
                                clientsResultSet.getBoolean("ENABLED"))
                );

        } finally {
            JdbcUtils.closeResultSet(clientsResultSet);
            JdbcUtils.closeStatement(preparedStatement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }
}
