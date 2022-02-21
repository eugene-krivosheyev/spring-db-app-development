package com.acme.dbo.it.account.dao;

import com.acme.dbo.client.dao.JdbcTemplateClientRepository;
import com.acme.dbo.client.dao.JdbcTemplateCustomRowMapperClientRepository;
import com.acme.dbo.client.dao.NamedParameterJdbcTemplateBeanRowMapperClientRepository;
import com.acme.dbo.client.dao.TxJdbcClientRepository;
import com.acme.dbo.client.domain.Client;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("it")
@FieldDefaults(level = PRIVATE)
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional //TODO NB!
public class SpringTxTest {
    static final String SQL_SELECT_CLIENTS_COUNT_BY_LOGIN = "SELECT COUNT(*) FROM CLIENT WHERE LOGIN = ?";
    static final String DUMMY_LOGIN = "dummy login";

    @Autowired TxJdbcClientRepository txJdbcClientRepository; // TODO NB! pure implementation
    @Autowired JdbcTemplateClientRepository jdbcTemplateClientRepository;
    @Autowired JdbcTemplateCustomRowMapperClientRepository jdbcTemplateCustomRowMapperClientRepository;
    @Autowired NamedParameterJdbcTemplateBeanRowMapperClientRepository namedParameterJdbcTemplateBeanRowMapperClientRepository;
    @Autowired JdbcTemplate jdbcTemplate;

    @Test
    @Order(1)
    @Rollback(true) //TODO Note default
    public void shouldUpdateDb() throws SQLException {
        Client clientDto = new Client(null, DUMMY_LOGIN, true);

        txJdbcClientRepository.save(clientDto);
        jdbcTemplateClientRepository.save(clientDto);
        jdbcTemplateCustomRowMapperClientRepository.save(clientDto);
        namedParameterJdbcTemplateBeanRowMapperClientRepository.save(clientDto);

        assertThat(
                jdbcTemplate.queryForObject(SQL_SELECT_CLIENTS_COUNT_BY_LOGIN, Integer.class, DUMMY_LOGIN
        )).isEqualTo(4);
    }

    @Test
    @Order(2)
    public void shouldNotLeaveSideEffects() {
        assertThat(
                jdbcTemplate.queryForObject(SQL_SELECT_CLIENTS_COUNT_BY_LOGIN, Integer.class, DUMMY_LOGIN
        )).isZero();
    }
}
