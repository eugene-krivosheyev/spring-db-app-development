package com.acme.dbo.client.dao;

import com.acme.dbo.client.domain.Client;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
public class NamedParameterJdbcTemplateBeanRowMapperClientRepository implements ClientRepository {
    @Autowired final NamedParameterJdbcTemplate namedParameterJdbcTemplate; //TODO NB!
    @Autowired final BeanPropertyRowMapper<Client> clientBeanPropertyRowMapper;

    @Override
    public Long save(Client clientDto) throws SQLException {
        final KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                "INSERT INTO CLIENT(LOGIN, ENABLED) VALUES(:login, :enabled)", //TODO NB named params!
                new BeanPropertySqlParameterSource(clientDto), //TODO NB!
                generatedKeyHolder,
                new String[] { "ID" } // TODO lowercase for Postgres, uppercase for Derby!
        );

        return generatedKeyHolder.getKey().longValue(); //generatedKey
    }

    @Override
    public Collection<Client> findAllClients() throws SQLException {
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM CLIENT",
                clientBeanPropertyRowMapper //TODO NB Potential excessive resource allocation with `new` and thread-safety issue
        );
    }

    @Override
    public Optional<Client> findById(Long primaryKey) {
        return Optional.ofNullable(
                namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM CLIENT WHERE ID = :id",
                    Map.of("id", primaryKey),
                        clientBeanPropertyRowMapper
        ));
    }
}
