package es.codeurjc.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "es.codeurjc.repositories")
@EntityScan(basePackages = "es.codeurjc.classes")
public class ApplicationConfig {
}
