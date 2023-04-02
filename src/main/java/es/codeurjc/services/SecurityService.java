package es.codeurjc.services;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Service that manage all about security
 * @author gu4re
 * @version 1.0
 */
@Service
public class SecurityService {
	/**
	 * Private constructor avoiding initialize of Service
	 */
	private SecurityService(){}
	
	/**
	 * Calculates the SHA-256 hash of the given plain text
	 * and returns it encoded in Base64.
	 * @param plainText The plain text to be hashed
	 * @return The plain text hashed
	 * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
	 */
	public static String hashCode(@NotNull String plainText) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(plainText.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(hashBytes);
    }
}