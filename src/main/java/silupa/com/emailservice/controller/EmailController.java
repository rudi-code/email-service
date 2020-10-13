package silupa.com.emailservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import silupa.com.emailservice.service.EmailService;

@RestController
@CrossOrigin(origins = "*")
public class EmailController {
	
//	@Value("${gmail.username}") 
//	private String username;
//	
//	@Value("${gmail.password}") 
//	private String password;

	@GetMapping("/important")
	public String getImporttantMessage() {
		return "bisa";
	}
	
	@PostMapping("/sendEmail")
	public String sendEmail() {
		EmailService email = new EmailService();
		if(email.sendEmail()) return "success";
		else return "failed";
		
	}
}
