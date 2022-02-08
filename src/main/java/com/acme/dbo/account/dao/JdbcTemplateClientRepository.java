package com.acme.dbo.account.dao;

import com.acme.dbo.account.domain.Client;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
public class JdbcTemplateClientRepository implements ClientRepository {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
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
        return generatedKeyHolder.getKey().longValue();
    }

    public Collection<Client> findAllClients() throws SQLException {
        log.warn("findAllClients() using JdbcTemplateClientRepository");
        Object[] argsForPreparedStatement = null;
        return jdbcTemplate.queryForList("SELECT ID, LOGIN, ENABLED FROM CLIENT", argsForPreparedStatement)
                .stream().map(recordMap -> new Client(
                    (Long) recordMap.get("ID"),
                    (String) recordMap.get("LOGIN"),
                    (Boolean) recordMap.get("ENABLED")
                )).collect(Collectors.toList());
    }
}
