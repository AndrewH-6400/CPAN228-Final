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

    //igdb url
    private final String IGDB_URl = "https://api.igdb.com/v4";
    //these are the fields that we are using for our games model
    private final String GAME_FIELDS = "name,summary,involved_companies,genres,themes,cover";

    //this is the main method that will be used by others to pull game information
    //a body that will define the search term, and the fields to be returned
    //a route to define the proper endpoint
    //the generic game search returns ids for things like, genres, themes, and companies involved and to get specific information we need to make other calls
    public JSONArray searchIGDB(String body, String route) {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.post(IGDB_URl+route)
                    .header("Client-ID", clientId)
                    .header("Authorization", accessToken)
                    .header("Accept", "application/json")
                    .body(body)
                    .asJson();

            //return the array of responses, some methods do not use this and will instead just use the first within the array
            return jsonResponse.getBody().getArray();


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

    //use theme id to find theme name
    public String searchIGDBTheme(int themeID){
        String body = "fields name; where id = "+themeID+";";
        JSONObject jsonObject = searchIGDB(body,"/themes").getJSONObject(0);
        return jsonObject.get("name").toString();
    }

    //will take an involved companies id and return a list, this list will have the company name
    //as well as whether or not they were a developer and whether or not they were a publisher
    public List<String> searchIGDBInvCo(int invCoID){
        //first call to get the company id, and their roles in the associated game
        String body = "fields company,developer,publisher; where id = "+invCoID+";";
        JSONObject jsonObject = searchIGDB(body,"/involved_companies").getJSONObject(0);
        //change booleans to strings
        String isDev = jsonObject.get("developer").toString();
        String isPub = jsonObject.get("publisher").toString();
        //make a second call to get the company name
        body = "fields name; where id = "+jsonObject.get("company")+";";
        String company = searchIGDB(body,"/companies").getJSONObject(0).get("name").toString();

        return Arrays.asList(company,isDev,isPub);
    }

    //get and return the height width and url for the cover of the video game
    public List<String> searchIDGBCover(int coverID){
        String body = "fields height,width,url; where id = "+coverID+";";
        JSONObject jsonObject = searchIGDB(body, "/covers").getJSONObject(0);
        return Arrays.asList(jsonObject.get("height").toString(),jsonObject.get("width").toString(),jsonObject.get("url").toString());
    }

    //save the game
    //this will use some of the smaller methods (genre id to name) to find user readable information to save to the database
    //the id field can be null, if it is null an id is generated otherwise this is an update method
    public Game saveIGDBbyTitle(String title, String id) {
        //new game object to populate
        Game game = new Game();
        //call the api using a title and take the first option given
        String body = "fields "+GAME_FIELDS+"; where name = "+'"'+title+'"'+";";
        //jsonObject will hold the response from the search IGDB method
        JSONObject jsonObject = searchIGDB(body,"/games").getJSONObject(0);

        //exchange int array from /games query to string arraylist for our database
        //array list to be populated with associated genres
        ArrayList<String> genre = new ArrayList<>();
        //for each genre id associated with the game make a call to the api to get their name
        //then add that information to the database
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
        //for each involved company id given by original /games call
        for (Object c : jsonObject.getJSONArray("involved_companies")){
            //response string to hold the name, and roles to be sorted into corresponding list(s)
            List<String> res = searchIGDBInvCo((Integer) c);
            //if dev is true (which is always stored at the index of 1) then add the name (always stored at 0) to the dev list
            //if pub is true (always stored at 2) add the name to the pub list
            if (res.get(1).equalsIgnoreCase("true")){
                devs.add(res.get(0));
            } else if (res.get(2).equalsIgnoreCase("true")) {
                pubs.add(res.get(0));
            }
        }
        //get the cover information and add it to the list cover
        //there will only ever be one cover so not much is needed
        List<String> cover = searchIDGBCover((Integer) jsonObject.get("cover"));



        //the title is given from the original call and just needs to be retrieved
        game.setTitle(jsonObject.get("name").toString());
        //summary also just needs to be retrieved from jsonObject
        game.setDescription(jsonObject.get("summary").toString());
        //the rest are the local variables that have been filled with the user readable information
        game.setGenres(genre);
        game.setThemes(theme);
        game.setDeveloper(devs);
        game.setPublisher(pubs);
        game.setCover(cover);


        if (id == null){
            //generate new object id and convert it to a string
            //then save
            game.setId(new ObjectId().toString());
            System.out.println(game);
            //save the game
            gamesService.saveGame(game);
        } else {
            //use update to overwrite existing documents
            game.setId(id);
            //update the game
            gamesService.updateGame(game);
        }
        //return it
        return game;
    }
}
