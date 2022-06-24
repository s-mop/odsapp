package com.tw.poc.odspoc;

import com.mongodb.WriteConcern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteConcernResolver;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.convert.MongoConverter;

@SpringBootApplication
@Configuration
@Slf4j
public class OdspocApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdspocApplication.class, args);
	}

	@Bean
	public WriteConcernResolver writeConcernResolver() {
		return action -> {
			System.out.println("Using Write Concern of Acknowledged");
			return WriteConcern.ACKNOWLEDGED;
		};
	}

	@Bean
	MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory, MongoConverter converter) {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
		log.debug("Setting WriteConcern statically to ACKNOWLEDGED");
		mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		// Version 2: provide a WriteConcernResolver, which is called for _every_ MongoAction
		// which might degrade performance slightly (not tested)
		// and is very flexible to determine the value
		mongoTemplate.setWriteConcernResolver(action -> {
			log.debug("Action {} called on collection {} for document {} with WriteConcern.MAJORITY. Default WriteConcern was {}", action.getMongoActionOperation(), action.getCollectionName(), action.getDocument(), action.getDefaultWriteConcern());
			return WriteConcern.ACKNOWLEDGED;
		});
		mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
		return mongoTemplate;
	}
}
