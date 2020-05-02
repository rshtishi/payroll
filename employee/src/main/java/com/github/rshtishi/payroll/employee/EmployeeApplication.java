package com.github.rshtishi.payroll.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@SpringBootApplication
@EnableResourceServer
@EnableBinding(Source.class)
public class EmployeeApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Employee Application Started");
		SpringApplication.run(EmployeeApplication.class, args);
	}
}