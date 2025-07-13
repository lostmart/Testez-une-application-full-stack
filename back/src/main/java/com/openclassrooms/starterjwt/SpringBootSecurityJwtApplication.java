package com.openclassrooms.starterjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootSecurityJwtApplication {
	public static void main(String[] args) {

		

		Dotenv dotenv = Dotenv.configure()
				.directory(".")
				.filename(".env")
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}
}
