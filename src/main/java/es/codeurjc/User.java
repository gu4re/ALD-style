package es.codeurjc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class User {
	private String name;
	private String mail;
	private String password;
	public User(String name, String password){
		this.name = name;
		this.password = password;
	}
	public User(String name, String password, String mail){
		this(name, password);
		this.mail = mail;
	}
}
