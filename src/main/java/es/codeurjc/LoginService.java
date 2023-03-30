package es.codeurjc;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class LoginService {
	private static Map<String, String> usersMap;
	private LoginService(){}
	public static boolean authenticate(User user){
		return usersMap.get(user.getName()) != null && usersMap.get(user.getName()).equals(user.getPassword());
	}
}
