package com.acme.dbo.client.dao;

import com.acme.dbo.client.domain.Client;
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
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class JdbcTemplateCustomRowMapperClientRepository implements ClientRepository {
    @Autowired final JdbcTemplate jdbcTemplate;
    SimpleJdbcInsert simpleJdbcInsert;
    ClientRowMapper clientRowMapper;

    @PostConstruct
    public void init() {
        clientRowMapper = new ClientRowMapper(); //TODO move to spring configuration
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate) //TODO move to spring configuration
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
        //TODO NB Potential excessive resource allocation with `new` and thread-safety issue
        return jdbcTemplate.query("SELECT ID, LOGIN, ENABLED FROM CLIENT", clientRowMapper, null);
    }

    @Override
    public Optional<Client> findById(Long primaryKey) throws SQLException {
        return jdbcTemplate.query("SELECT ID, LOGIN, ENABLED FROM CLIENT WHERE ID = ?", clientRowMapper, primaryKey)
                .stream().findFirst();
    }
}
