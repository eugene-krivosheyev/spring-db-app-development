package com.acme.dbo.config;

import com.acme.dbo.client.domain.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
public class ApplicationConfig {
    @Bean
    BeanPropertyRowMapper<Client> clientBeanPropertyRowMapper() {
        return new BeanPropertyRowMapper<>(Client.class);
    }
}
