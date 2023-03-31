package es.codeurjc.services;

import es.codeurjc.classes.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
	private static Map<String, String> usersMap;
	private AuthService(){}
	public static void run(){
		usersMap = new HashMap<>();
		usersMap.put("pedro", "1234");
	}
	public static void stop(){
	
	}
	public static boolean authenticate(@NotNull User user){
		return usersMap.get(user.getMail()) != null && usersMap.get(user.getMail()).equals(user.getPassword());
	}
}
