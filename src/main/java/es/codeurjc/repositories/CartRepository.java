package es.codeurjc.repositories;

import es.codeurjc.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Cart Repository/Interface that links the cart's table with backend
 * @author gu4re
 * @version 1.0
 */
public interface CartRepository extends JpaRepository<CartEntity, CartEntity.CartEntityId> {
}
