package com.Humber.FinalProject.CPAN228_FinalProject.config;

import com.Humber.FinalProject.CPAN228_FinalProject.repositories.secondary.UserRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackageClasses = UserRepository.class,
        mongoTemplateRef = "secondaryMongoTemplate"
)
public class MongoSecondaryConfig {

        @Value("${database.password}")
        private String password;

        @Value("${database.username}")
        private String username;



        @Bean(name = "secondaryMongoClient")
        public MongoClient mongo() {
            ConnectionString connectionString = new ConnectionString ("mongodb+srv://"+username+":"+password+"@cluster0.cud60vo.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");

            MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();

            return MongoClients.create(mongoClientSettings);
        }


    @Bean(name = "secondaryMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier("primaryMongoClient") MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, "Users");
    }


    @Bean(name = "secondaryMongoTemplate")
        public MongoTemplate mongoTemplate(@Qualifier("secondaryMongoDBFactory")MongoDatabaseFactory mongoDatabaseFactory) throws Exception {
            return new MongoTemplate(mongoDatabaseFactory);
        }
}
