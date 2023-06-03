package es.codeurjc.services;

import es.codeurjc.entities.UserEntity;
import es.codeurjc.exceptions.UserNotFoundException;
import es.codeurjc.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service that manage all about authenticate and serves the process of login
 * and register of someone inside the Web Application
 * @author gu4re
 * @version 2.0
 */
@Service
public class UserService {
	/**
	 * Repository of the user's table that links mySQL to backend
	 */
	@Autowired
	@SuppressWarnings("unused")
	private UserRepository userRepository;
	
	/**
	 * Private constructor avoiding initialize of Service
	 */
	private UserService(){}
	
	/**
	 * Carriers the authenticate process of the web application,
	 * checking if the user passed as parameter is already registered
	 * @param email The email to be checked
	 * @param rawPassword The plain password to be checked
	 * @return <a style="color: #E89B6C; display: inline;">True</a> if the user is already
	 * registered otherwise <a style="color: #E89B6C; display: inline;">False</a>
	 */
	public boolean authenticate(@NotNull String email, @NotNull String rawPassword){
		if (userRepository.findById(email).isPresent())
			return userRepository.findById(email).get().getPassword()
					.equals(SecurityService.hashCode(rawPassword));
		return false;
	}
	
	/**
	 * Check if a user exists or not
	 * @param email The user's email to be checked
	 * @return <a style="color: #E89B6C; display: inline;">True</a> if the user exists
	 * otherwise <a style="color: #E89B6C; display: inline;">False</a>
	 */
	public boolean userExists(@NotNull String email){
		return userRepository.existsById(email);
	}
	
	/**
	 * Register the user inside the database
	 * @param newUserEntity User to be added to the database
	 */
	public void addUser(UserEntity newUserEntity){
		userRepository.save(newUserEntity);
	}
	
	/**
	 * Changes the password of the user that has email passed as parameter
	 * @param email the user's mail
	 * @param newRawPassword The new password that is going to be set
	 * @return A ResponseEntity based of what happened in the Service
	 * returning <a style="color: #E89B6C; display: inline;">200 OK</a> if all goes good,
	 * <a style="color: #E89B6C; display: inline;">400 Bad Request</a> if not and
	 * otherwise <a style="color: #E89B6C; display: inline;">404 Not Found</a> if any resource is not able
	 */
	public @NotNull ResponseEntity<Void> changePassword(@NotNull String email, @NotNull String newRawPassword){
		try{
			Optional<UserEntity> optionalUser = userRepository.findById(email);
			if (optionalUser.isEmpty())
				throw new UserNotFoundException();
			optionalUser.get().setPassword(SecurityService.hashCode(newRawPassword));
			userRepository.save(optionalUser.get());
			return ResponseEntity.ok().build();
		} catch (UserNotFoundException e){
			Logger.getLogger("No mapping found for that credentials.");
			return ResponseEntity.badRequest().build();
		}
	}
	
	/**
	 * Remove a user passed an email
	 * @param email the email passed by the controller
	 */
	public void removeUser(@NotNull String email){
		userRepository.delete(userRepository.findById(email).get());
	}
}