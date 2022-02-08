package com.acme.dbo.account.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Client {
    final Long id;
    @NonNull final String login;
    Boolean enabled;
}
