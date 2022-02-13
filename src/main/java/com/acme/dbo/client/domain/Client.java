package com.acme.dbo.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)

@Entity
@NamedQuery(name = "findAllClients", query = "SELECT c FROM Client c")
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull String login;
    Boolean enabled;
}
