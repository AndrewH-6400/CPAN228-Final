package com.Humber.FinalProject.CPAN228_FinalProject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

//lombok data and constructors
@Data
@AllArgsConstructor
@NoArgsConstructor
//tells this is to be associated with mongodb, config sets up the cluster connection and database/repository this is needed for the collection
@Document(collection = "UserInfo")
public class MyUser {
    //fields can be added to or subtracted from without affecting later installments
    @MongoId
    private String id;
    private String password;
    private String fname;
    private String lname;
    private String email;
    private String about;

    @DocumentReference
    private List<MyUserGames> myUserGamesIDs;
}
