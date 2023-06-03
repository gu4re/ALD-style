package es.codeurjc.repositories;

import es.codeurjc.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Order Repository/Interface that links the order's table with backend
 * @author gu4re
 * @version 1.0
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
