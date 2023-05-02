package es.codeurjc.repositories;

import es.codeurjc.entities.ShoesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Cart Repository/Interface that links the cart's table with backend
 * @author gu4re
 * @version 1.0
 */
public interface CartRepository extends JpaRepository<ShoesEntity, ShoesEntity.ShoesEntityId> {
	@Query("SELECT c FROM ShoesEntity c ORDER BY c.price DESC")
	List<ShoesEntity> findAllOrderedByASCPrice();
	
	@Query("SELECT c FROM ShoesEntity c ORDER BY c.price ASC")
	List<ShoesEntity> findAllOrderedByDESCPrice();
}
