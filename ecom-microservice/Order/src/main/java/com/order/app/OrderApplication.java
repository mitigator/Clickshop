package com.order.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.modelmapper.ModelMapper;


@SpringBootApplication(scanBasePackages = "com.order")
@ComponentScan(basePackages = "com.order")
@EnableJpaRepositories(basePackages = "com.order.repository")
@EntityScan(basePackages = "com.order.entity")
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(OrderApplication.class);
		Environment env = app.run(args).getEnvironment();
		System.out.println("Authentication running on port: "+ env.getProperty("server.port"));	
	}
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
	

}

