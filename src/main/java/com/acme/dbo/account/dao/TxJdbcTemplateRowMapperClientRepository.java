package com.acme.dbo.account.dao;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Repository
@FieldDefaults(level = PRIVATE)
@Slf4j
@Transactional //TODO NB!
public class TxJdbcTemplateRowMapperClientRepository extends JdbcTemplateRowMapperClientRepository {
    @Autowired
    public TxJdbcTemplateRowMapperClientRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }
}
