package com.Humber.FinalProject.CPAN228_FinalProject;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import static com.mongodb.client.model.Filters.eq;

@SpringBootApplication
public class Cpan228FinalProjectApplication {

	public static void main(String[] args) {

//		String password = "4DtUk77R41Q3xAa9";
//		String uri = "mongodb+srv://drewh6400:4DtUk77R41Q3xAa9@cluster0.cud60vo.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
//
//		try (MongoClient mongoClient = MongoClients.create(uri)) {
//			MongoDatabase database = mongoClient.getDatabase("Games");
//			MongoCollection<Document> Games = database.getCollection("Games");
//
//			Document doc = Games.find(eq("title","Hollow Knight")).first();
//
//			if (doc != null) {
//				System.out.println(doc.toJson());
//			} else {
//				System.out.println("No Game found");
//			}
//
//		}
		SpringApplication.run(Cpan228FinalProjectApplication.class, args);
	}

}
