package co.edu.unisimon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
	
	@GetMapping("/healthcheck")
	public String healthCheck() {
		return "Ok";
	}

}
