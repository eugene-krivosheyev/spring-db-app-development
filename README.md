Работа с РСУБД в Spring
=======================

Recordings
==========
- [01.02](https://us02web.zoom.us/rec/share/q4hpzHjbMFa94VSmDToWICO01WT7oC7vFzILa-O2PKbI0oYH-obSCzyoEgh6bNyl.u7LnxFSTmL8auWAO)
- [03.02](https://us02web.zoom.us/rec/share/BrhwBcDwL1uXsSKRYcE-RRQeYkk4wbgOzOzdj1XY0bpvtbM2O8zdfl1BzJutepDz.bh8xuLJjPhGzRewg)
- [08.02](https://us02web.zoom.us/rec/share/zcuIl-Otg07iUt-Xkm-dJZSYyTVX0fuoTrFgGAlWd0ycpHOZ3GlaQrKS_cfkEaxI.7jmDgKeP64XyqbkM)

Prerequisites
=============
- [ ] JDK11
- [ ] IntelliJ IDEA Ultimate
- [ ] Lombok plugin + Enable annotation processing
- [x] JPA Buddy plugin
- [x] Docker
- [x] Forked this repo
- [ ] Forked [agile-practices-application](https://github.com/eugene-krivosheyev/agile-practices-application) repo

Agenda <sup>24</sup>
======

Spring frameworks recap <sup>3</sup>
-----------------------
- [ ] Why Spring Core?
- [ ] Why Spring MVC?
- [ ] Why Spring Boot?
- [ ] How to configure the app?
- [ ] DataSource configuration 
- [ ] Connection pool configuration

Ключевые концепции РСУБД и SQL recap <sup>2</sup>
------------------------------------
- [ ] Подключение к БД из IDEA
- [ ] Драйвер
- [ ] JDBC URL и параметры подключения
- [ ] База, каталог, схема
- [ ] Объекты схемы
- [ ] DDL vs DML

JDBC API <sup>3</sup>
--------
- [ ] Driver
- [ ] Connection
- [ ] Connection Pool
- [ ] Statement and PreparedStatement
- [ ] ResultSet
- [ ] [Java to SQL types mapping](https://docs.oracle.com/javase/1.5.0/docs/guide/jdbc/getstart/table8.5.html)
- [ ] Closing resources

JDBC в Spring <sup>3</sup>
-------------
- [ ] DB schema versioning with Spring Boot module
- [ ] TestContainers library with external DB
- [ ] CRUD @Repository and its common API
- [ ] @Autowired of dependent beans
- [ ] [Key components](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#jdbc-choose-style)
- DataSource
- JdbcTemplate
- RowMapper
- SimpleJdbcInsert
- [`org.springframework.jdbc.object`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/object/package-summary.html) package

[JDBC-транзакции и Spring](https://www.marcobehler.com/guides/spring-transaction-management-transactional-in-depth) <sup>2</sup>
------------------------
- [ ] Понятие транзакции
- [ ] Как включить в raw JDBC
- [ ] Уровни изоляции транзакций
- [ ] Управление изоляцией в SQL-запросе: `FOR UPDATE`/`FOR SHARE`
- [ ] [Управление транзакциями в Spring](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#transaction)
- [ ] ThreadLocal magic
- [ ] [Декларативное управление транзакциями в Spring](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#transaction-declarative-annotations)
- [ ] Why declarative transactions?
- Non-atomic operations within Repository
- Propagation policy
- Rollback policy
- Abstraction for different tx managers: managed jdbc connections, JPA, JTA, etc.

JPA в Spring <sup>6</sup>
------------

JPA-транзакции <sup>2</sup>
--------------

Spring Data JPA <sup>3</sup>
---------------
