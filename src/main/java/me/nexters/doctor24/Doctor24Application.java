package me.nexters.doctor24;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableScheduling
@EnableBatchProcessing
@EnableReactiveFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Doctor24Application {

	public static void main(String[] args) {
		SpringApplication.run(Doctor24Application.class, args);
	}

}
