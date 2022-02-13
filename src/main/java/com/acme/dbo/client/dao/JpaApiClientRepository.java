package com.acme.dbo.client.dao;

import com.acme.dbo.client.domain.Client;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class JpaApiClientRepository implements ClientRepository {
    @Getter
    @Autowired final EntityManager em;

    /**
     * em.persist(clientDto);
     */
    @Override
    public Long save(Client clientDto) throws SQLException {
        return em.merge(clientDto).getId();
    }

    @Override
    public Collection<Client> findAllClients() throws SQLException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Optional<Client> findById(Long primaryKey) throws SQLException {
        return Optional.ofNullable(
            em.find(Client.class, primaryKey)
        );
    }
}
