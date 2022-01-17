package com.example.task42;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@SpringBootApplication

public class Task42Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Task42Application.class, args);
	}

}
