package com.Humber.FinalProject.CPAN228_FinalProject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Games")
public class Game {
    private String id;
    private String title;
    private String description;
    private String developer;
    private String publisher;
    private List<String> genres;
    private List<String> themes;

}
