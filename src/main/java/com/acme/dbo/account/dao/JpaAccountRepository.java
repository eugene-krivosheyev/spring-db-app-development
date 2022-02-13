package com.acme.dbo.account.dao;

import com.acme.dbo.account.domain.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
@Slf4j
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class JpaAccountRepository {
    @Getter
    @Autowired final EntityManager em;

    public Collection<Account> findAllAccounts() {
        return em.createQuery("SELECT a FROM Account a", Account.class)
                .getResultList();
    }
}
