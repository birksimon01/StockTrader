package sbirk.stocks.toolbox;

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
	public void test () {
		sendUpdate("STOCKTRADER: Shares Bought", "StockTrader has bought x shares of stock y for z amount of money");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendError("An exception has been thrown in StockTrader\r\nSTATUS: up\r\nsbirk.stocks.dao.YFParser.getLiveQuote() has failed");
	}
}
