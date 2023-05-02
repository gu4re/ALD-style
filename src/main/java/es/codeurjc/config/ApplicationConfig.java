package es.codeurjc.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class for SpringBootApplication
 * @author gu4re
 * @version 1.0
 */
@Configuration
@EnableJpaRepositories(basePackages = "es.codeurjc.repositories")
@EntityScan(basePackages = "es.codeurjc.entities")
public class ApplicationConfig {
}
