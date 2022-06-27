package hr.fer.progi.oneclick.humanitarnisetacipasa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class HumanitarniSetaciPasaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumanitarniSetaciPasaApplication.class, args);
	}

	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}

}
