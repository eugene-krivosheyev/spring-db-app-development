Работа с РСУБД в Spring
=======================

Recordings
==========
- [01.02](https://us02web.zoom.us/rec/share/q4hpzHjbMFa94VSmDToWICO01WT7oC7vFzILa-O2PKbI0oYH-obSCzyoEgh6bNyl.u7LnxFSTmL8auWAO)
- [03.02](https://us02web.zoom.us/rec/share/BrhwBcDwL1uXsSKRYcE-RRQeYkk4wbgOzOzdj1XY0bpvtbM2O8zdfl1BzJutepDz.bh8xuLJjPhGzRewg)
- [08.02](https://us02web.zoom.us/rec/share/zcuIl-Otg07iUt-Xkm-dJZSYyTVX0fuoTrFgGAlWd0ycpHOZ3GlaQrKS_cfkEaxI.7jmDgKeP64XyqbkM)
- [10.02](https://us02web.zoom.us/rec/share/lIf7yuJ2gbuh1I8VA2s41DibdN7HW255Dm9DVRKvt-L8Q2lYoPTq2s1JFWW-pmLQ.7y82kPHzPjn1KpsY)
- [15.02](https://us02web.zoom.us/rec/share/4s8WDwWpBE0SZ4_0yfkaPUZS1NZg4lZ1gHndYTCRd-BUFV7DQcXMDoQumfxYSz-x.K_Bv8TU99hMmSREr)
- [17.02](https://us02web.zoom.us/rec/share/LguNjvhbvodTYGiGI3koHAdTQ93DtE5L3sSU1oXLiNY5ywlqUcHa6M5vNv_rdp32.KvO6g2-XCF0Oe0_H)

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
- **NamedParameterJdbcTemplate**
- [`org.springframework.jdbc.object`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/object/package-summary.html) package

[Транзакции в Spring](https://www.marcobehler.com/guides/spring-transaction-management-transactional-in-depth) <sup>2</sup>
------------------------
- [ ] Понятие транзакции в Spring
- TX в JDBC
- TX в JPA
- `TransactionManager` implementations
- [ ] Как управлять транзакциями в raw JDBC
- Уровни изоляции транзакций для всех запросов через данное соединение
- Управление изоляцией в самом SQL-запросе: `FOR UPDATE`/`FOR SHARE`

- [ ] [Как управлять транзакциями в Spring](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#transaction)
- `ThreadLocal` magic
- [Декларативное управление транзакциями в Spring](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#transaction-declarative-annotations)
- [Кто реализует декларативную магию?](https://habr.com/ru/post/532000/)
- [Декларативное управление поведением при ошибках](https://www.baeldung.com/transaction-configuration-with-jpa-and-spring)
- [ ] Why declarative transactions?
- Non-atomic operations within Repository
- Propagation policy
- Rollback policy
- Abstraction for different tx managers: managed jdbc connections, JPA, JTA, etc.
- [ ] [Типовые ошибки](https://habr.com/ru/company/otus/blog/574470/)

JPA в Spring <sup>6</sup>
------------
- [ ] Управление авто-конфигурацией ORM в конфиге Spring Boot
- [ ] Архитектура ORM: JPA & JPA Provider
- Entity vs ValueObject/DTO
- Persistence Context 
- [ ] [JPA API](https://www.tutorialspoint.com/jpa/jpa_introduction.htm)
- EntityManager
- Entity
- OQL-запросы
- Criteria API
- [ ] Логическая конфигурация Entity
- первичный ключ
- ленивость полей
- [связи и ленивость](https://www.baeldung.com/hibernate-lazy-eager-loading)
- наследование
- [ ] Физическая конфигурация Entity
- Таблицы
- Поля
- Индексы
- [ ] Validation API
- Когда активируется валидация (спойлер: не единожды)
- Кто валидирует
- Как описать правила валидации
- [ ] Caching API
- ORM кешируют объекты по своей архитектуре
- ORM так же могут [кешировать Persistence Contexts](https://www.tutorialspoint.com/hibernate/hibernate_caching.htm) с итоговыми [тремя уровнями кеширования](https://habr.com/ru/post/135176/) 

JPA-транзакции <sup>2</sup>
--------------
- [ ] Принципиально иная реализация транзакций в ORM vs JDBC
- [ ] Optimistic vs Pessimistic стратегии
- [ ] Поле версии у сущностей
- [ ] Управление транзакциями в Spring Boot конфигурации

Spring Data JPA <sup>3</sup>
---------------
- [ ] Задачи автогенерации
- [ ] Декларация Spring Data Repository
- [ ] Что наследуем
- [ ] Как можем задать свои методы на соглашениях
- [ ] Как можем задать свои методы на OQL-запросе
- [x] Генерация контроллеров
