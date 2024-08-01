package com.Humber.FinalProject.CPAN228_FinalProject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

//Lombok model data and args constructors
@Data
@AllArgsConstructor
@NoArgsConstructor
//similar to JPA entity, but for mongodb
//collection specifies the collection, database information is associated through config -> repository -> this model
@Document(collection = "Games")
public class Game {
    //all information to be saved, can be changed without worry of affecting previous entries
    @MongoId
    private String id;
    private String title;
    private String description;
    private List<String> developer;
    private List<String> publisher;
    private List<String> genres;
    private List<String> themes;
}
