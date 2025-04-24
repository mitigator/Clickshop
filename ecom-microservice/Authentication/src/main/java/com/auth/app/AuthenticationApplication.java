package com.auth.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "com.auth")
@ComponentScan(basePackages = "com.auth")
@EnableJpaRepositories(basePackages = "com.auth.repository")
@EntityScan(basePackages = "com.auth.entity")
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AuthenticationApplication.class);
		Environment env = app.run(args).getEnvironment();
		System.out.println("Authentication running on port: "+ env.getProperty("server.port"));	}

}
