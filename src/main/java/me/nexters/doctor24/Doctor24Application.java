package me.nexters.doctor24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Doctor24Application {

	public static void main(String[] args) {
		SpringApplication.run(Doctor24Application.class, args);
	}

}
