package com.acme.dbo.account.dao;

import com.acme.dbo.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Collection;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Repository
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
public class JdbcTemplateAccountRepository implements AccountRepository {
    @Autowired JdbcTemplate jdbcTemplate;

    public Collection<Account> findAllAccounts() throws SQLException {
        log.warn("using JdbcTemplateAccountRepository");

        return jdbcTemplate.queryForList("SELECT * FROM ACCOUNT").stream().map(record -> new Account(
                (Long)record.get("ID"),
                (Long)record.get("CLIENT_ID"),
                (Double)record.get("AMOUNT")
        )).collect(Collectors.toList());
    }
}
