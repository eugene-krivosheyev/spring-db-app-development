package com.acme.dbo.it.account.dao;

import com.acme.dbo.account.dao.JpaAccountRepository;
import com.acme.dbo.account.domain.Account;
import com.acme.dbo.client.domain.Client;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // vs @JdbcTest
@ActiveProfiles("it")
@FieldDefaults(level = PRIVATE)
@Slf4j
@Transactional // because of EM API
public class AccountRepositoryTest {
    @Autowired JpaAccountRepository accounts;

    @Test
    public void shouldGetAccountsAndItsClientWhenPrepopulatedDb() throws SQLException {
        assertThat(accounts.findAllAccounts()).contains(
            new Account(
                    1L,
                    new Client(1L, "admin@acme.com", true),
                    BigDecimal.valueOf(0L))
        );
    }
}
