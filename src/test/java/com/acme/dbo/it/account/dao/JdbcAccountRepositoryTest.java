package com.acme.dbo.it.account.dao;

import com.acme.dbo.account.dao.AccountRepository;
import com.acme.dbo.account.dao.JdbcAccountRepository;
import com.acme.dbo.account.domain.Account;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("it")
@SpringBootTest
@Slf4j
@FieldDefaults(level = PRIVATE)
public class JdbcAccountRepositoryTest {
    @Autowired AccountRepository accounts;

    @Test
    public void shouldGetAllAccountsWhenPrepopulatedDb() throws SQLException {
        assertThat(accounts.findAllAccounts()).contains(
                new Account(1, 1, 0),
                new Account(2, 1, 100),
                new Account(3, 2, 200)
        );
    }
}
