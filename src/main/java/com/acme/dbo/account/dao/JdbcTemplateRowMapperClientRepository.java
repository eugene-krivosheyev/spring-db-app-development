package com.acme.dbo.account.dao;

import com.acme.dbo.account.domain.Client;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
public class JdbcTemplateRowMapperClientRepository implements ClientRepository {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired final JdbcTemplate jdbcTemplate;
    SimpleJdbcInsert simpleJdbcInsert;
    ClientRowMapper clientRowMapper;

    @PostConstruct
    public void init() {
        clientRowMapper = new ClientRowMapper();
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CLIENT")
                .usingColumns("login", "enabled")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(Client clientDto) throws SQLException {
        return simpleJdbcInsert.executeAndReturnKey(
                Map.of(
                        "LOGIN", clientDto.getLogin(),
                        "ENABLED", true
                )
        ).longValue();
    }

    @Override
    public Collection<Client> findAllClients() throws SQLException {
        final Object[] argsForPreparedStatement = null;
        return jdbcTemplate.query("SELECT ID, LOGIN, ENABLED FROM CLIENT", clientRowMapper, argsForPreparedStatement);
    }
}
