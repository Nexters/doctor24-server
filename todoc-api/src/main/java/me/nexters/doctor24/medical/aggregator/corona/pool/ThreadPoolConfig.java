package me.nexters.doctor24.medical.aggregator.corona.pool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author manki.kim
 */
@Configuration
public class ThreadPoolConfig {
	@Bean
	public Executor inquiryPool() {
		return Executors.newFixedThreadPool(4);
	}
}
