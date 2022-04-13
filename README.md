Работа с РСУБД в Spring
=======================
Real life problem oriented training that helps you become skilled database-driven backend developer.

Prerequisites
=============
- [ ] JDK11
- [ ] IntelliJ IDEA Ultimate
- [ ] Lombok plugin + Enable annotation processing
- [ ] JPA Buddy plugin
- [ ] Docker
- [ ] Forked this repo
- [ ] Forked [agile-practices-application](https://github.com/eugene-krivosheyev/agile-practices-application) repo

Agenda <sup>17 hours</sup>
======

How to test and debug database driven applications with IDEA? <sup>1,5</sup>
-------------------------------------------------------------
- [ ] How to connect to database with IDEA built-in DB client?
- What is and How to set up driver? 
- What is and How to set db connection URL?
- [ ] What are common db objects?
- DB User
- Database
- Catalog
- Schema
- Table, Raw/Record, Field/Column 
- Trigger, Stored procedure
- [ ] How to set up database with DDL SQL?
- [ ] How to modify data with DML SQL?
- [ ] How to select data from database?

Why Spring frameworks and How to configure the application? <sup>1,5</sup>
-----------------------------------------------------------
- [ ] What is the common design for backend application?
- [ ] Why Spring Core?
- [ ] Why Spring MVC?
- [ ] Why Spring Boot?
- [ ] How to configure the whole Spring Boot application?
- [ ] How application does connect to database?
- [ ] How to configure DataSource configuration?
- [ ] How to configure Connection Pool configuration?
- [ ] How to switch test and production configurations with configuration profiles?

How to set up automatic database schema updates? <sup>1</sup>
------------------------------------------------
- [ ] Why database migrations?
- [ ] How to set up database migrations with stand-alone Liquibase?
- [ ] How to set up database migrations with Liquibase as part of Spring Boot application? 

How to design common REST API backend with Spring MVC and Boot? <sup>1,5</sup>
---------------------------------------------------------------
- [ ] What is the common application architecture?
- [ ] How to design Controllers?
- [ ] How to design Services?
- [ ] How to design DTOs?
- MapStruct
- ModelMapper
- [ ] How to design Repository?
- [ ] How to design Entities?

How to implement common CRUD features with JPA? <sup>1,5</sup>
-----------------------------------------------
- [ ] Why JPA?
- [ ] How to configure JPA provider with Spring Boot?
- [ ] How to configure Entity class?
- @Entity, @Id, @Transient
- @Column, @Table, @Basic
- @Index
- [ ] How does JPA provider deal with objects as part of Persistence Context?
- [ ] How to implement simple Create feature with JPA?
- [ ] How to implement simple Read feature with JPA?
- [ ] How to implement simple Update feature with JPA?
- [ ] How to implement simple Delete feature with JPA?
- [ ] How to trace JPA provider database activity for CRUD features?

How to implement domain with complex identities? <sup>1</sup>
------------------------------------------------
- [ ] How to choose identity option?
- Natural
- Surrogate
- [ ] How to implement composite key? 
- [ ] How to implement @Generated strategies?
- [ ] How to implement equals() and hashCode() methods?

How to implement domain with complex queries? <sup>1</sup>
---------------------------------------------
- [ ] How to get entities with common JPQL queries?
- [ ] How to get entities with named JPQL queries?
- [ ] How to get entities with native queries?
- [ ] How to get entities with Criteria API?

How to implement domain with complex associations? <sup>2</sup>
--------------------------------------------------
- [ ] Why entity embedding?
- [ ] How to implement embedded entity?
- [ ] Why entity associations?
- [ ] How to implement unidirectional one-to-one association?
- [ ] How to implement unidirectional one-to-many association?
- [ ] How to implement unidirectional many-to-one association?
- [ ] How to implement unidirectional many-to-many association?
- [ ] How to implement bidirectional one-to-one association?
- [ ] How to implement bidirectional one-to-many association?
- [ ] How to implement bidirectional many-to-one association?
- [ ] How to implement bidirectional many-to-many association?
- [ ] How to choose Eager or Lazy loading?

How to implement complex domain with inheritance? <sup>0,5</sup>
-------------------------------------------------
- [ ] Why inheritance?
- [ ] How to implement inheritance strategies?
- MappedSuperclass
- Single Table
- Joined Table
- Table per Class

How to implement complex domain validation? <sup>0,5</sup>
-------------------------------------------
- [ ] Why data validation?
- [ ] How to configure automatic data validation?
- [ ] How to configure data validation rules?

How to implement data consistency with JPA transactions? <sup>1,5</sup>
--------------------------------------------------------
- [ ] Why database consistency for concurrent and multi-source applications?
- [ ] How does database itself implement consistency with JDBC transactions?
- [ ] How to tune up JDBC transactions with isolation levels?
- [ ] How does JPA provider implement consistency with JPA transactions?
- optimistic strategy and @Version
- pessimistic strategy
- [ ] How to manage JPA transactions with program code?
- [ ] How to manage JPA transactions with Spring configuration?
- [ ] How to manage JPA transaction propagation across application?

How to automate development with Spring Data JPA? <sup>1,5</sup>
-------------------------------------------------
- [ ] Why Spring Data JPA?
- [ ] How to configure code generation?
- [ ] How to implement common CRUD Repository?
- [ ] How to declare custom Repository methods?
- [ ] How to customize queries for Repository methods?
- [ ] How to customize result types for Repository methods?
- [ ] How to implement Pagination pattern?

How to implement automated integration tests with Spring Boot and TestContainers? <sup>2</sup>
---------------------------------------------------------------------------------
- [ ] How to choose from Unit and Integration tests?
- [ ] How to configure application for testing with profiles?
- [ ] How does containerization work?
- [ ] How to configure custom container with TestContainers API?
- [ ] How to configure PostgreSQL container with TestContainers API?
- [ ] How to configure PostgreSQL container with custom driver?
- [ ] How to initialize database for test with simple DDL? 
- [ ] How to initialize database for test with Liquibase provisioning?
- [ ] How to make tests isolated with simple DML? 
- [ ] How to make tests isolated with automatic transaction management? 
