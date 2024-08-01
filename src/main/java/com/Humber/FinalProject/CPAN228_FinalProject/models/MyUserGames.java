package com.Humber.FinalProject.CPAN228_FinalProject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "UserGames")
public class MyUserGames {
    @MongoId
    private ObjectId id;
    private String userId;
    private String gameId;
    private int rating;
    private int playtime;
    private Boolean isFavourite;
}
