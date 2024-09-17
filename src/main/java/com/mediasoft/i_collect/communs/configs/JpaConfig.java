package com.mediasoft.i_collect.communs.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    AuditorAware<Long> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
