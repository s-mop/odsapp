package com.tw.poc.odspoc;

import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@Slf4j
public class OdspocApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdspocApplication.class, args);
	}

	@Bean
	MongoClientSettings mongoClientSettings() {
		MongoClientSettings.Builder builder = MongoClientSettings.builder();
		builder.writeConcern(WriteConcern.UNACKNOWLEDGED);
		return builder.build();
	}
}
