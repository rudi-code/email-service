package silupa.com.emailservice.service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sun.mail.imap.IMAPFolder;

/**
 * This program demonstrates how to get and send e-mail messages
 *
 * @author Rudi purnomo
 * @date 16-jul-2020
 *
 */

public class EmailService {
	
	String strClassPath = System.getProperty("user.dir");
	
	String username  = "rudipurnomo.mail";
	String password = "123jkl.gmail.com";
	
	String to = "rudi4purnomo@gmail.com";
	String from = username+"@gmail.com";
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	//method for read email
	public boolean readEmail() {
		
		System.out.println("Start.");
		
		boolean bol = true;
		
		// set properties
		Properties props = new Properties();
    	props.put("mail.store.protocol","imaps");
    	
    	//get session
        Session session = Session.getDefaultInstance(props, null);
        
        
		try {
			// connect to gmail
			Store store = session.getStore("imaps");
	        store.connect("imap.gmail.com", username, password);

	        // get inbox from email(all/...)
			Folder inbox = (IMAPFolder) store.getFolder("[Gmail]/Important");
			
//		    Folder inbox = store.getFolder("inbox");
				  /* Others GMail folders :
				   * [Gmail]/All Mail   This folder contains all of your Gmail messages.
				   * [Gmail]/Drafts     Your drafts.
				   * [Gmail]/Sent Mail  Messages you sent to other people.
				   * [Gmail]/Spam       Messages marked as spam.
				   * [Gmail]/Starred    Starred messages.
				   * [Gmail]/Trash      Messages deleted from Gmail.
				       */
			
			inbox.open(Folder.READ_ONLY);
			
			//get list message inbox
			Message messages[] = inbox.getMessages();
			            
			System.out.println("No of Messages : " + inbox.getMessageCount());
			System.out.println("No of Unread Messages : " + inbox.getUnreadMessageCount());
			  
			for (int i=0; i < messages.length; ++i) {
				
				//get message
				Message message = messages[messages.length-1-i];
				
				//check if wanna filter by subject
				if(message.getSubject().contains("e-Statement Service for BCA RDN TAPRES")) {
					
					// get multipart from content message
					Multipart multipart = (Multipart) message.getContent();
					// get from address
					Address[] fromAddress = message.getFrom();
					String from = fromAddress[0].toString();
					// get date send
					String sendDate = message.getSentDate().toString();
				
					int numberOfParts = multipart.getCount();
					
					for (int partCount = 0; partCount < numberOfParts; partCount++) {
						MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(partCount);
						
						// save attachment
				        if (part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
				        	
				        	String fileName = part.getFileName();
				            
				        	// save file in directory
				            part.saveFile("D:\\Workspace\\MyProject\\Web-Development\\BackEnd-Development\\Email\\email-service\\Attachment" + 
				            		File.separator +
				            		fileName);
				            System.out.println("file name : "+fileName);
				            
				            // delete message
//                        	message.setFlag(Flags.Flag.DELETED, true);
				                
				    	}
					}  
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			bol = false;
		}
		
		return bol;
		
	}

//	public boolean sendEmail() {
//		sim
//		
//		
//		return true;
//	}
	
public boolean sendEmail() {
		
		boolean bol = true;
		
        // Get system properties
        Properties properties = System.getProperties();
        
        // Setup mail server
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
		
        
     // Get the Session object.// and pass 
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }

        });
        
        //session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");
            
            message.setText("Hello, this is sample for to check send "
                    + "email using JavaMailAPI "); 

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            bol = false;
        }
        
        
		return bol;
	}

}
