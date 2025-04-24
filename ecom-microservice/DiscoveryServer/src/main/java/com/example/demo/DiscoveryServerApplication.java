package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DiscoveryServerApplication.class);
        Environment env = app.run(args).getEnvironment();
        System.out.println("Discovery Server started on port: " + env.getProperty("server.port"));
    }

}
