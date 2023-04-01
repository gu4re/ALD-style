package es.codeurjc.services;

import es.codeurjc.classes.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Service that manage all about authenticate and serves the process of login
 * and register of someone inside the Web Application
 * @author gu4re
 * @version 1.0
 */
@Service
public class AuthService implements Serializable {
	
	/**
	 * Fake database that controls the users registered in the App
	 */
	private static Map<String, String> usersMap;
	
	/**
	 * Private constructor avoiding initialize of Service
	 */
	private AuthService(){}
	
	/**
	 * Starts the AuthService connecting the database to the Service
	 */
	@SuppressWarnings(value = "unchecked")
	public static void run(){
		try (ObjectInputStream ois = new ObjectInputStream(
		        new FileInputStream("src/main/resources/database/database.bin"))) {
		    Object obj = ois.readObject();
		    if (obj instanceof Map<?,?>) {
		        usersMap = (Map<String, String>) obj;
		    } else {
		        usersMap = new HashMap<>();
		    }
		} catch (IOException e) {
		    usersMap = new HashMap<>();
		} catch (ClassNotFoundException e){
			Logger.getLogger("Unable to read content of database.");
		}
	}
	
	/**
	 * Stops the AuthService before the Spring Application stops, saving the database
	 * to a binary file
	 */
	@PreDestroy
	public static void stop(){
		try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("src/main/resources/database/database.bin"))) {
            oos.writeObject(usersMap);
        } catch (IOException e) {
            Logger.getLogger("Unable to reach file to write on.");
        }
	}
	
	/**
	 * Carriers the authenticate process of the web application,
	 * checking if the user passed as parameter is already registered
	 * @param user The user to be checked
	 * @return True if the user is already registered otherwise false
	 */
	public static boolean authenticate(@NotNull User user){
		try{
			return usersMap.get(user.getMail()) != null
		       && usersMap.get(user.getMail()).equals(SecurityService.hashCode(user.getPassword()));
		} catch (NoSuchAlgorithmException e){
			Logger.getLogger("Failed hash function.");
		}
		return false;
	}
	
	/**
	 * Check if a user exists or not
	 * @param user The user to be checked
	 * @return True if the user exists otherwise false
	 */
	public static boolean userExists(@NotNull User user){
		return usersMap.get(user.getMail()) != null;
	}
	
	/**
	 * Register the user inside the database
	 * @param user The user to be added into database
	 */
	public static void addUser(@NotNull User user){
		try {
			usersMap.put(user.getMail(), SecurityService.hashCode(user.getPassword()));
		} catch (NoSuchAlgorithmException e){
			Logger.getLogger("Failed hash function.");
		}
	}
}