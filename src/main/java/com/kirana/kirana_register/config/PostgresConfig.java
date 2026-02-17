package com.kirana.kirana_register.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.kirana.kirana_register.repository.postgres"
)
@EntityScan(
        basePackages = "com.kirana.kirana_register.entity.postgres"
)
public class PostgresConfig {
}
