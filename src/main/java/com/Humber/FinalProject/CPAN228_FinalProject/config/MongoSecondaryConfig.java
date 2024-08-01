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

//config file
@Configuration
//needed as we are using multiple databases
//base package classes associates repository and its classes(or models) with this mongo template
@EnableMongoRepositories(basePackages = {"com.Humber.FinalProject.CPAN228_FinalProject.repositories.secondary"},
        mongoTemplateRef = "secondaryMongoTemplate"
)
public class MongoSecondaryConfig {

    //database password and username from app.properties
    @Value("${database.password}")
    private String password;

    @Value("${database.username}")
    private String username;



    //named bean to help specify what will use what
    //this could possibly be simplified by removing this bean and un-naming the MongoClient bean from primaryconfig as their isn't anything specific to it
    @Bean(name = "secondaryMongoClient")
    public MongoClient mongo() {
        //connection string to mongodb cluster
        ConnectionString connectionString = new ConnectionString ("mongodb+srv://"+username+":"+password+"@cluster0.cud60vo.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        //return mongodb client
        return MongoClients.create(mongoClientSettings);
    }

    //takes mongoclient and creates database factory with associated database
    @Bean(name = "secondaryMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(
            //specific mongoclient bean to be used
            @Qualifier("primaryMongoClient") MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, "Users");
    }

    //uses the mongodatabasefactory and returns template for Users database
    @Bean(name = "secondaryMongoTemplate")
        public MongoTemplate mongoTemplate(@Qualifier("secondaryMongoDBFactory")MongoDatabaseFactory mongoDatabaseFactory) throws Exception {
            return new MongoTemplate(mongoDatabaseFactory);
        }
}
