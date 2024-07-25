package com.Humber.FinalProject.CPAN228_FinalProject.config;

import com.Humber.FinalProject.CPAN228_FinalProject.repositories.primary.GameRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


//for help lol but basically client -> database -> collection
//https://www.mongodb.com/docs/drivers/java/sync/current/usage-examples/findOne/
@Configuration
@EnableMongoRepositories(basePackageClasses = GameRepository.class,
        mongoTemplateRef = "primaryMongoTemplate"
)
public class MongoPrimaryConfig {

    @Value("${database.password}")
    private String password;

    @Value("${database.username}")
    private String username;

    @Primary
    @Bean(name = "primaryMongoClient")
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString ("mongodb+srv://"+username+":"+password+"@cluster0.cud60vo.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Primary
    @Bean(name = "primaryMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier("primaryMongoClient") MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, "Games");
    }


    @Primary
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("primaryMongoDBFactory")MongoDatabaseFactory mongoDatabaseFactory) throws Exception {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
