package es.codeurjc;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
	private static Map<String, String> usersMap;
	private LoginService(){}
	public static boolean authenticate(User user){
		if (usersMap == null){
			usersMap = new HashMap<>();
			return false;
		}
		return usersMap.get(user.getMail()) != null && usersMap.get(user.getMail()).equals(user.getPassword());
	}
}
