package com.acme.dbo.account.dao;

import com.acme.dbo.account.domain.Account;

import java.sql.SQLException;
import java.util.Collection;

public interface AccountRepository {
    Collection<Account> findAllAccounts() throws SQLException;
}
