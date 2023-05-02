package es.codeurjc.repositories;

import es.codeurjc.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User Repository/Interface that links the user's table with backend
 * @author gu4re
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {

}
