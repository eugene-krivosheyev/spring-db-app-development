package com.acme.dbo.account.dao;

import com.acme.dbo.account.domain.Client;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;

@Primary
@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
public class JdbcClientRepository implements ClientRepository {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired final DataSource dataSource;

    @Override
    public Long save(Client clientDto) throws SQLException {
        try (
                final Connection connection = dataSource.getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CLIENT(LOGIN, ENABLED) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
        ) {

            preparedStatement.setString(1, clientDto.getLogin());
            preparedStatement.setBoolean(2, clientDto.getEnabled());
            preparedStatement.executeUpdate();

            try(final ResultSet keysResultSet = preparedStatement.getGeneratedKeys()) {
                keysResultSet.next();
                return keysResultSet.getLong(1);
            }
        }
    }

    @Override
    public Collection<Client> findAllClients() throws SQLException {
        log.debug("findAllClients() using JdbcClientRepository");

        try (
                final Connection connection = dataSource.getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID, LOGIN, ENABLED FROM CLIENT");
                final ResultSet clientsResultSet = preparedStatement.executeQuery();
        ) {

            final Collection<Client> clients = new ArrayList<>();
            while (clientsResultSet.next()) {
                clients.add(new Client(
                        clientsResultSet.getLong("ID"),
                        clientsResultSet.getString("LOGIN"),
                        clientsResultSet.getBoolean("ENABLED"))
                );
            }

            return clients;
        }
    }
}
