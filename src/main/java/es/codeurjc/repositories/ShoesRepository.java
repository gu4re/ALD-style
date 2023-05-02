package es.codeurjc.repositories;

import es.codeurjc.entities.ShoesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Shoes Repository/Interface that links the shoes's table with backend
 * @author gu4re
 * @version 1.0
 */
public interface ShoesRepository extends JpaRepository<ShoesEntity, String>{
}
