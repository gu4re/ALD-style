package es.codeurjc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Application with run method
 * @author gu4re
 * @version 1.0
 */
@SpringBootApplication
public class Application {
	/**
	 * main method of SpringBootApplication
	 * @param args args passed to run method
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);}
}