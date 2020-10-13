package silupa.com.emailservice;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.sun.mail.imap.IMAPFolder;

import silupa.com.emailservice.service.EmailService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
//		EmailService email = new EmailService();
//		if(email.readEmail()) {
//			System.out.println("DONE.");
//		}
		
//		if(email.sendEmail()) {
//			System.out.println("DONE.");
//		}

	}
	
}