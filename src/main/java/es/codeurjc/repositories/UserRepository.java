package es.codeurjc.repositories;

import es.codeurjc.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User Repository/Interface that links the user's table with backend
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
