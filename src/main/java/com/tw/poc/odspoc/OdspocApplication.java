package com.tw.poc.odspoc;

import com.mongodb.MongoClientSettings;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.MongoPropertiesClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

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
		builder.readPreference(ReadPreference.secondary());
		return builder.build();
	}
	@Bean
	MongoPropertiesClientSettingsBuilderCustomizer mongoPropertiesCustomizer(MongoProperties properties,
																			 Environment environment) {
		return new MongoPropertiesClientSettingsBuilderCustomizer(properties, environment);
	}
}
