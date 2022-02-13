package com.acme.dbo.it.account.dao;

import com.acme.dbo.client.dao.ClientRepository;
import com.acme.dbo.client.domain.Client;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // vs @JdbcTest
@ActiveProfiles("it")
@FieldDefaults(level = PRIVATE)
@Slf4j
@Transactional // because of EM API
public class ClientRepositoryTest {
    @Autowired ClientRepository clients;
    @Autowired JdbcTemplate jdbcTemplate;

    @Test
    public void shouldGetAllClientsWhenPrepopulatedDb() throws SQLException {
        assertThat(clients.findAllClients()).contains(
                new Client(1L, "admin@acme.com", true),
                new Client(2L, "client@acme.com", true),
                new Client(3L, "disabled@acme.com", false)
        );
    }

    @Test
    public void shouldGetClientByIdWhenPrepopulatedDb() throws SQLException {
        assertThat(clients.findById(1L).orElseThrow())
                .isEqualTo(new Client(1L, "admin@acme.com", true));
    }

    @Test
    public void shouldGetClientWhenInserted() throws SQLException {
        Long clientId = clients.save(new Client(null,"dummy@email.com", true));

        assertThat(
            jdbcTemplate.query("SELECT * FROM CLIENT WHERE ID = ?", new BeanPropertyRowMapper<>(Client.class), clientId)
        ).contains(
            new Client(clientId, "dummy@email.com", true)
        );
    }
}
