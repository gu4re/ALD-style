package es.codeurjc.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Service that manage all mapping about mailService
 * specially used in forgotPassword
 * @author gu4re
 * @version 1.0
 */
@Service
public class MailService {
	/**
	 * Private constructor to avoid initializing
	 */
	private MailService(){}
	
	/**
	 * This function gets the path to the body's HTML and read it and return it into String format
	 * @param pathBodyHTML The path where is the body's HTML file
	 * @return the String format of the mail's body
	 * @throws IOException In case we are not able to reach the HTML file
	 */
	private static @NotNull String bodyHTMLtoString(String pathBodyHTML) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(pathBodyHTML))) {
			StringBuilder mailStringBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				mailStringBuilder.append(line);
			}
			return mailStringBuilder.toString();
		}
	}
	
	/**
	 * Treats all related to sending an email. It is variable and applicable to any controller that possess
	 * a javaMailSender
	 * @param subject The subject of the mail
	 * @param mailFrom From what direction is going to be sent the email
	 * @param mailTo To what direction is going to be sent
	 * @param pathBodyHTML The path where's the body style
	 * @param javaMailSender the javaMailSender that allows the sending
	 * @return A ResponseEntity based of what happened in the Service
	 * returning <a style="color: #E89B6C; display: inline;">200 OK</a> if all goes good,
	 * <a style="color: #E89B6C; display: inline;">400 Bad Request</a> if not and
	 * otherwise <a style="color: #E89B6C; display: inline;">404 Not Found</a> if any resource is not able
	 */
	public static @NotNull ResponseEntity<Void> send(String subject, String mailFrom, String mailTo,
	                                                 String pathBodyHTML, @NotNull JavaMailSender javaMailSender){
		try{
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(mailTo);
			helper.setFrom(new InternetAddress(mailFrom, "Zapatillas ALD"));
			helper.setSubject(subject);
			helper.setText(bodyHTMLtoString(pathBodyHTML), true);
			javaMailSender.send(message);
			return ResponseEntity.ok().build();
		} catch (MessagingException e) {
			Logger.getLogger("Error has occurred during sending the mail.");
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		} catch (IOException e){
			Logger.getLogger("Problem occurred formatting body or building 'from' field");
			return ResponseEntity.notFound().build();
		}
	}
}
