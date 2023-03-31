package es.codeurjc;

import lombok.Data;

@Data
public class User {
	private String name;
	private String mail;
	private String password;
	public User(String mail, String password){
		this.mail = mail;
		this.password = password;
	}
	public User(String mail, String password, String name){
		this(mail, password);
		this.name = name;
	}
}
