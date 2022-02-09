package com.acme.dbo.it.account.dao;

import com.acme.dbo.account.dao.TxJdbcClientRepository;
import com.acme.dbo.account.dao.TxJdbcTemplateClientRepository;
import com.acme.dbo.account.dao.TxJdbcTemplateRowMapperClientRepository;
import com.acme.dbo.account.domain.Client;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("it")
@FieldDefaults(level = PRIVATE)
@Slf4j
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpringTxTest {
    static final String DUMMY_LOGIN = "dummy login";

    @Autowired TxJdbcClientRepository txJdbcClientRepository;
    @Autowired TxJdbcTemplateClientRepository txJdbcTemplateClientRepository;
    @Autowired TxJdbcTemplateRowMapperClientRepository txJdbcTemplateRowMapperClientRepository;
    @Autowired JdbcTemplate jdbcTemplate;

    @Test
    @Order(1)
    public void shouldUpdateDb() throws SQLException {
        Client clientDto = new Client(null, DUMMY_LOGIN, true);
        txJdbcClientRepository.save(clientDto);
        txJdbcTemplateClientRepository.save(clientDto);
        txJdbcTemplateRowMapperClientRepository.save(clientDto);

        assertThat(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CLIENT WHERE LOGIN = '" + DUMMY_LOGIN + "'",
                Integer.class
        )).isEqualTo(3);
    }

    @Test
    @Order(2)
    public void shouldNotLeaveSideEffects() {
        assertThat(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CLIENT WHERE LOGIN = '" + DUMMY_LOGIN + "'",
                Integer.class
        )).isZero();
    }
}
