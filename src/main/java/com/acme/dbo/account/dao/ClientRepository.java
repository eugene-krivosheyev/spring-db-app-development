package com.acme.dbo.account.dao;

import com.acme.dbo.account.domain.Client;

import java.sql.SQLException;
import java.util.Collection;

public interface ClientRepository {
    Long save(Client toSave) throws SQLException;
    Collection<Client> findAllClients() throws SQLException;
}
