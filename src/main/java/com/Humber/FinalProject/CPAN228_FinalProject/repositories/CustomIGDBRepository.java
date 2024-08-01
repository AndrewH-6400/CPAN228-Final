package com.Humber.FinalProject.CPAN228_FinalProject.repositories;

import com.Humber.FinalProject.CPAN228_FinalProject.models.Game;


import com.Humber.FinalProject.CPAN228_FinalProject.services.GamesService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CustomIGDBRepository {

    @Value("${client.id}")
    private String clientId;

    @Value("${client.access.token}")
    private String accessToken;

    private final GamesService gamesService;

    public CustomIGDBRepository(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    private final String IGDB_URl = "https://api.igdb.com/v4";
    private final String GAME_FIELDS = "name,summary,involved_companies,genres,themes";
//    private String id; will be generated by mongodb later
//    private String title; simple field
//    private String description; simple field, could use a fallback to other provided fields
//    private String developer; take the ids and search involved_companies then find the one that is a developer and use the company field to search companies to find the info
//    private String publisher; same as developer
//    private List<String> genres; will return genre ids that will have to be searched
//    private List<String> themes; same as genres

    //search IGDB for game by title
    //alright so gameplan for this part
    //this should return a JSONArray object thing, then other functions will use that and getJSONObject(0) to get the information
    //to I will also need to create another to return information like genres etc
    public JSONArray searchIGDB(String body, String route) {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.post(IGDB_URl+route)
                    .header("Client-ID", clientId)
                    .header("Authorization", accessToken)
                    .header("Accept", "application/json")
                    .body(body)
                    .asJson();

            JSONArray jsonArray = jsonResponse.getBody().getArray();
            //System.out.println(jsonArray);
            return jsonArray;


        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    //use searchIGDB to get the genre ids and return the names
    public String searchIGDBGenre(int genreID){
        String body = "fields name; where id = "+genreID+";";
        JSONObject jsonObject = searchIGDB(body,"/genres").getJSONObject(0);
        return jsonObject.get("name").toString();
    }

    public String searchIGDBTheme(int themeID){
        String body = "fields name; where id = "+themeID+";";
        JSONObject jsonObject = searchIGDB(body,"/themes").getJSONObject(0);
        return jsonObject.get("name").toString();
    }

    public List<String> searchIGDBInvCo(int invCoID){
        String body = "fields company,developer,publisher; where id = "+invCoID+";";
        JSONObject jsonObject = searchIGDB(body,"/involved_companies").getJSONObject(0);
        String isDev = jsonObject.get("developer").toString();
        String isPub = jsonObject.get("publisher").toString();
        body = "fields name; where id = "+jsonObject.get("company")+";";
        String company = searchIGDB(body,"/companies").getJSONObject(0).get("name").toString();

        return Arrays.asList(company,isDev,isPub);

    }

    public Game saveIGDBbyTitle(String title) {
        Game game = new Game();
        String body = "fields "+GAME_FIELDS+"; where name = "+'"'+title+'"'+";";
        JSONObject jsonObject = searchIGDB(body,"/games").getJSONObject(0);

        //exchange int array from /games query to string arraylist for our database
        ArrayList<String> genre = new ArrayList<>();
        for (Object o : jsonObject.getJSONArray("genres")) {
            genre.add(searchIGDBGenre((Integer) o));
        }

        //exchange int array for corresponding strings
        ArrayList<String> theme = new ArrayList<>();
        for (Object t : jsonObject.getJSONArray("themes")){
            theme.add(searchIGDBTheme((Integer) t));
        }


        //check each involved company then add them to their corresponding list(s)
        ArrayList<String> devs = new ArrayList<>();
        ArrayList<String> pubs = new ArrayList<>();
        for (Object c : jsonObject.getJSONArray("involved_companies")){
            List<String> res = searchIGDBInvCo((Integer) c);
            if (res.get(1).equalsIgnoreCase("true")){
                devs.add(res.get(0));
            } else if (res.get(2).equalsIgnoreCase("true")) {
                pubs.add(res.get(0));
            }
        }
        game.setId(new ObjectId().toString());
        game.setTitle(jsonObject.get("name").toString());
        game.setDescription(jsonObject.get("summary").toString());
        game.setGenres(genre);
        game.setThemes(theme);
        game.setDeveloper(devs);
        game.setPublisher(pubs);
        System.out.println(game);
        gamesService.saveGame(game);
        return(game);
    }
}
