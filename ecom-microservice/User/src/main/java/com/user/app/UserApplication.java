package com.user.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.user")
@ComponentScan(basePackages = "com.user")
@EnableJpaRepositories(basePackages = "com.user.repository")
@EntityScan(basePackages = "com.user.entity")
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(UserApplication.class);
        Environment env = app.run(args).getEnvironment();
        System.out.println("User Service running on port: " + env.getProperty("server.port"));
    	}

}
