package es.codeurjc.repositories;

import es.codeurjc.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User Repository/Interface that links the user's table with backend
 */
public interface UserRepository extends JpaRepository<User, String> {

}
