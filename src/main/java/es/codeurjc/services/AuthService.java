package es.codeurjc.services;

import es.codeurjc.classes.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class AuthService implements Serializable {
	// Map<Email, Password>
	private static Map<String, String> usersMap;
	private AuthService(){}
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
		} catch (IOException | ClassNotFoundException e) {
		    usersMap = new HashMap<>();
		}
	}
	@PreDestroy
	public static void stop(){
		try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("src/main/resources/database/database.bin"))) {
            oos.writeObject(usersMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static boolean authenticate(@NotNull User user){
		try{
			return usersMap.get(user.getMail()) != null
		       && usersMap.get(user.getMail()).equals(hashCode(user.getPassword()));
		} catch (NoSuchAlgorithmException e){
			Logger.getLogger("Failed hash function.");
		}
		return false;
	}
	public static boolean userExists(@NotNull User user){
		return usersMap.get(user.getMail()) != null;
	}
	private static String hashCode(@NotNull String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(hashBytes);
    }
	public static void addUser(@NotNull User user){
		try {
			usersMap.put(user.getMail(), hashCode(user.getPassword()));
		} catch (NoSuchAlgorithmException e){
			Logger.getLogger("Failed hash function.");
		}
	}
}
