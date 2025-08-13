package pl.lodz.p.it.functionalfood.investigator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableAspectJAutoProxy
public class InvestigatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestigatorApplication.class, args);
	}

}
