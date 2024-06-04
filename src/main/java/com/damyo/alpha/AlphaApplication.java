package com.damyo.alpha;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
public class AlphaApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlphaApplication.class, args);
	}
}
