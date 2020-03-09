package me.nexters.doctor24;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableScheduling
@EnableReactiveFeignClients
@EnableBatchProcessing
@EnableReactiveMongoRepositories(basePackages = "me.nexters.domain")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class TodocBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodocBatchApplication.class, args);
    }
}
