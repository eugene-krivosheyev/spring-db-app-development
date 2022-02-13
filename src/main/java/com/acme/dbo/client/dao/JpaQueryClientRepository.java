package com.acme.dbo.client.dao;

import com.acme.dbo.client.domain.Client;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;

@Repository
@FieldDefaults(level = PRIVATE)
@Slf4j
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class JpaQueryClientRepository extends JpaApiClientRepository {
    public JpaQueryClientRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Collection<Client> findAllClients() throws SQLException {
        return getEm().createNamedQuery("findAllClients", Client.class) // or em.createQuery()
                .getResultList();
    }
}
