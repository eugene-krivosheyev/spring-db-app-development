package com.acme.dbo.client.dao;

import com.acme.dbo.client.domain.Client;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class JdbcTemplateClientRepository implements ClientRepository {
    @Autowired final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(Client clientDto) throws SQLException {
        final KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CLIENT(LOGIN, ENABLED) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, clientDto.getLogin());
                    preparedStatement.setBoolean(2, clientDto.getEnabled());
                    return preparedStatement;
                },
                generatedKeyHolder
        );

        return generatedKeyHolder.getKey().longValue(); // .getKeys().get("id") in case of PG
    }

    public Collection<Client> findAllClients() throws SQLException {
        Object[] argsForPreparedStatement = null;
        return jdbcTemplate.queryForList("SELECT ID, LOGIN, ENABLED FROM CLIENT", argsForPreparedStatement)
                .stream().map(recordMap -> new Client(
                    (Long) recordMap.get("ID"),
                    (String) recordMap.get("LOGIN"),
                    (Boolean) recordMap.get("ENABLED")
                )).collect(Collectors.toList());
    }

    @Override
    public Optional<Client> findById(Long primaryKey) throws SQLException {
        // TODO Note queryForObject is for single row/column queries!
        return jdbcTemplate.queryForList("SELECT ID, LOGIN, ENABLED FROM CLIENT WHERE ID = ?", primaryKey)
                .stream().map(recordMap -> new Client(
                        (Long) recordMap.get("ID"),
                        (String) recordMap.get("LOGIN"),
                        (Boolean) recordMap.get("ENABLED")
                )).findFirst();
    }
}
