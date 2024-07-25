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


//Config to set up mongodb
@Configuration
//needed as we are going to be using multiple databases from the same cluster
//base package class associates any models that repository interacts with into this config and template
@EnableMongoRepositories(basePackageClasses = GameRepository.class,
        mongoTemplateRef = "primaryMongoTemplate"
)
public class MongoPrimaryConfig {

    //password to database from app.properties
    @Value("${database.password}")
    private String password;

    //username from app.properties
    @Value("${database.username}")
    private String username;

    //primary as this is the primary mongodb database being setup
    @Primary
    //named to avoid any repeating names/mix-ups
    @Bean(name = "primaryMongoClient")
    //takes connection string and login info to give us a client
    public MongoClient mongo() {
        //connection string to the mongodb database for this project
        ConnectionString connectionString = new ConnectionString ("mongodb+srv://"+username+":"+password+"@cluster0.cud60vo.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");

        //client settings build with connectionString
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        //return the mongoClient
        return MongoClients.create(mongoClientSettings);
    }

    @Primary
    @Bean(name = "primaryMongoDBFactory")
    //takes the client connection and creates a database factory, this is not entirely necessary but recommended
    public MongoDatabaseFactory mongoDatabaseFactory(
            //specifies which mongoclient bean to use
            @Qualifier("primaryMongoClient") MongoClient mongoClient) {
        //returns database factory with specific database
        return new SimpleMongoClientDatabaseFactory(mongoClient, "Games");
    }


    @Primary
    @Bean(name = "primaryMongoTemplate")
    //takes databasefactory and returns mongo template for the Games database
    public MongoTemplate mongoTemplate(
            @Qualifier("primaryMongoDBFactory")MongoDatabaseFactory mongoDatabaseFactory) throws Exception {
        //returns mongoTemplate, needed for repository etc
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
