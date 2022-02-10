package com.acme.dbo.client.dao;

import com.acme.dbo.client.domain.Client;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface ClientRepository {
    Long save(Client clientDto) throws SQLException;
    Collection<Client> findAllClients() throws SQLException;
    Optional<Client> findById(Long primaryKey) throws SQLException;
}
