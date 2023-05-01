package es.codeurjc;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Configuration
@EnableJpaRepositories(basePackages = {"es.codeurjc.repositories"})
@EntityScan(basePackages = "es.codeurjc.classes")
@ComponentScan({"es.codeurjc.controllers", "es.codeurjc.services", "es.codeurjc.classes"})
@EnableTransactionManagement
public class ApplicationConfig {
 
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
}
