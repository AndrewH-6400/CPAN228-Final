package com.Humber.FinalProject.CPAN228_FinalProject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@AllArgsConstructor
@NoArgsConstructor
//this is apart of the Users database under the collection UserGames
@Document(collection = "UserGames")
public class MyUserGames {
    @MongoId
    private String id;
    private String userId;
    private String gameId;
    private int rating;
    private int playtime;
    private Boolean isFavourite;
}
