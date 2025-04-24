package com.admin.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.admin")
@ComponentScan(basePackages = "com.admin")
@EnableJpaRepositories(basePackages = "com.admin.repository")
@EntityScan(basePackages = "com.admin.entity")
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AdminApplication.class);
        Environment env = app.run(args).getEnvironment();
        System.out.println("User Service running on port: " + env.getProperty("server.port"));
	}

}
