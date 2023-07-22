package ch.stephan.chickenfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChickenFarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChickenFarmApplication.class, args);
	}

}
