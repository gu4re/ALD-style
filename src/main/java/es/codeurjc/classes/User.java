package es.codeurjc.classes;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Contains all the information a user can save inside the web application
 * @author gu4re
 * @version 1.2
 */
@Entity
@Table(name = "users")
@Data
public class User {
	/**
	 * E-mail of the user. Also, the primary key of the database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "email")
	private String email;
	
	/**
	 * Name of the user
	 */
	@Column(name = "username")
	private String name;

	/**
	 * Hashed password of the user
	 */
	@Column(name = "password")
	private String password;
	
	public User(){}
	
	/**
	 * Constructor of User with just email and password leaving
	 * the username param optional
	 * @param email The email to be assigned to the User
	 * @param password The raw password to be assigned to the User
	 */
	public User(String email, String password){
		this.email = email;
		this.password = password;
	}
	
	/**
	 * Constructor of User with email, password and name
	 * @param email The email to be assigned to the User
	 * @param password The raw password to be assigned to the User
	 * @param name The name to be assigned to the User
	 */
	public User(String email, String password, String name){
		this(email, password);
		this.name = name;
	}
}
