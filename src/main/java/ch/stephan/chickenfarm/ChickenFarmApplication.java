package ch.stephan.chickenfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "ch.stephan.chickenfarm")
public class ChickenFarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChickenFarmApplication.class, args);
	}

	@Bean
	CustomRestTemplateCustomizer customRestTemplateCustomizer() {
		return new CustomRestTemplateCustomizer();
	}

	@Bean
	@DependsOn(value = { "customRestTemplateCustomizer" })
	RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder(customRestTemplateCustomizer());
	}

	public class CustomRestTemplateCustomizer implements RestTemplateCustomizer {
		@Override
		public void customize(RestTemplate restTemplate) {
//			restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor());
		}
	}
}
