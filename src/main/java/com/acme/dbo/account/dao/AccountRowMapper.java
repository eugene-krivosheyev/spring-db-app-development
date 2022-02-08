package com.acme.dbo.account.dao;

import com.acme.dbo.account.domain.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Account(
                resultSet.getLong("ID"),
                resultSet.getLong("CLIENT_ID"),
                resultSet.getDouble("AMOUNT")
        );
    }
}
