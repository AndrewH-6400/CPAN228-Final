package com.Humber.FinalProject.CPAN228_FinalProject.config;

import com.Humber.FinalProject.CPAN228_FinalProject.repositories.UserRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackageClasses = UserRepository.class,
        mongoTemplateRef = "secondaryMongoTemplate"
)
@EnableConfigurationProperties
public class MongoSecondaryConfig {

        @Value("${database.password}")
        private String password;

        @Value("${database.username}")
        private String username;

        @Bean(name = "secondaryProperties")
        @ConfigurationProperties(prefix = "mongodb.secondary")
        public MongoProperties secondaryProperties() {
            return new MongoProperties();
        }

        @Bean(name = "secondaryMongoClient")
        public MongoClient mongo() {
            ConnectionString connectionString = new ConnectionString ("mongodb+srv://"+username+":"+password+"@cluster0.cud60vo.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");

            MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();

            return MongoClients.create(mongoClientSettings);
        }


        @Bean(name = "secondaryMongoTemplate")
        public MongoTemplate mongoTemplate() throws Exception {
            return new MongoTemplate(mongo(), "Users");
        }
}
