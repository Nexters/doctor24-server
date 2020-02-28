package me.nexters.doctor24.medical.corona.pool;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author manki.kim
 */
@Configuration
public class ThreadPoolConfig {

	private static final Integer DEFAULT_POOL_SIZE = Runtime.getRuntime().availableProcessors();

	@Bean
	public Executor inquiryPool() {
		return Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
	}
}
