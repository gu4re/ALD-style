package es.codeurjc.repositories;

import es.codeurjc.entities.OrderShoesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OrderShoes Repository/Interface that links the Order and Shoes table
 * to represent N:M relation
 * @author gu4re
 * @version 1.0
 */
public interface OrderShoesRepository extends JpaRepository<OrderShoesEntity, OrderShoesEntity.OrderShoesId> {
}
