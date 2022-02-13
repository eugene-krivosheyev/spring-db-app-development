package com.acme.dbo.client.dao;

import com.acme.dbo.client.domain.Client;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLException;
import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;

@Primary
@Repository
@FieldDefaults(level = PRIVATE)
@Slf4j
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class JpaCriteriaQueryClientRepository extends JpaQueryClientRepository {
    public JpaCriteriaQueryClientRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Collection<Client> findAllClients() throws SQLException {
        final CriteriaBuilder queryBuilder = getEm().getCriteriaBuilder();

        final CriteriaQuery<Client> query = queryBuilder.createQuery(Client.class);
        final Root<Client> queryRoot = query.from(Client.class);

        query.select(queryRoot);

        return getEm().createQuery(query).getResultList();
    }
}
