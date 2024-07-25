package com.Humber.FinalProject.CPAN228_FinalProject.config;

import com.Humber.FinalProject.CPAN228_FinalProject.repositories.GameRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties
public class MongoPrimaryConfig {

    @Value("${database.password}")
    private String password;

    @Value("${database.username}")
    private String username;

    @Bean(name = "primaryProperties")
    @ConfigurationProperties(prefix = "mongodb.primary")
    @Primary
    public MongoProperties primaryProperties() {
        return new MongoProperties();
    }

    @Bean(name = "primaryMongoClient")
    @Primary
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString ("mongodb+srv://"+username+":"+password+"@cluster0.cud60vo.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");



        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }


    @Bean(name = "primaryMongoTemplate")
    @Primary
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "Games");
    }

//    @Bean
//    public MongoCollection<Document> mongoGames(){
//        String connectionString = "mongodb+srv://"+username+":"+password+"@cluster0.cud60vo.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
//
//        ServerApi serverApi = ServerApi.builder()
//                .version(ServerApiVersion.V1)
//                .build();
//
//        MongoClientSettings settings = MongoClientSettings.builder()
//                //.retryWrites(true)
//                .applyConnectionString(new ConnectionString(connectionString))
//                //.applyToConnectionPoolSettings(())
//                .serverApi(serverApi)
//                .build();
//        MongoClient client = MongoClients.create(settings);
//        MongoDatabase database = client.getDatabase("Games");
//        return database.getCollection("Games");
//    }

}
