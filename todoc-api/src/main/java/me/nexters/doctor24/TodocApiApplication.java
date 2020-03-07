package me.nexters.doctor24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableReactiveFeignClients
@EnableReactiveMongoRepositories(basePackages = "me.nexters.domain")
@SpringBootApplication
public class TodocApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodocApiApplication.class, args);
    }
}
