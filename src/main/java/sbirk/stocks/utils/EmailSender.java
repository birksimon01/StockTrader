package sbirk.stocks.utils;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	
	private String host = "smtp.gmail.com";
	private String port = "587";
	
	private Properties properties;
	
	public EmailSender () {
		
		properties = System.getProperties();
		
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
	}
	
	public boolean sendEmail (String senderUsername, String senderPassword, String recipient, String title, String body) {
		
		Session session = Session.getDefaultInstance(properties,
			new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication () {
					return new PasswordAuthentication(senderUsername, senderPassword);
				}
		});
		
		try {
			
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderUsername));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(title);
			message.setText(body);
			
			Transport.send(message);
			
			System.out.println("Email successfully sent");
			
		} catch (MessagingException e) {
			System.out.println("Email failed to send");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean sendUpdate(String subject, String text) {
		String sender = "stocktraderstatus@gmail.com";
		String pass = "hopethisworks";
		String recipient = "birksimon01@gmail.com";
		return sendEmail(sender, pass, recipient, subject, text);
	}
	
	public boolean sendError(String message) {
		return sendUpdate("STOCKTRADER: ERROR", message);				
	}
	
	@PostConstruct
	private void startNotificationMessage () {
		sendUpdate("STOCKTRADER: Initializing Program", "STOCKTRADER is starting up now.\r\nEmailSender has been initialized, updates will be provided for every buy/sell order as well as any uncaught exception that's thrown");
	}
}
