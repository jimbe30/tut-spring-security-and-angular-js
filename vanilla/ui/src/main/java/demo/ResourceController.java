package demo;

import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
public class ResourceController {

	@RequestMapping("/resource")
	public Message home() {
		return new Message("Hello World");
	}
}

class Message {
	
	private String id = UUID.randomUUID().toString();
	private String content;

	public Message(String content) {
		this.content = content;
	}
	
	public String getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
}