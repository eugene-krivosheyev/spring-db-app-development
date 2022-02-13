package com.acme.dbo.account.domain;

import com.acme.dbo.client.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)

@Entity
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Client client;

    @Basic //optional
    BigDecimal amount;
}
