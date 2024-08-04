package com.Humber.FinalProject.CPAN228_FinalProject.controllers;

import com.Humber.FinalProject.CPAN228_FinalProject.models.Game;
import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUser;
import com.Humber.FinalProject.CPAN228_FinalProject.services.GamesService;
import com.Humber.FinalProject.CPAN228_FinalProject.services.IGDBService;
import com.Humber.FinalProject.CPAN228_FinalProject.services.MyUserService;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//For this file I need to set-up clearer endpoints as well as a search using custom queries
//I also want to implement error handling, so that in the case that something goes wrong I can communicate that to the client

//rest controller as we are only operating as a service for database integration
@RestController
//map requests through Database
@RequestMapping("/Database")
public class MongoController {

    //injecting games service and IGDB service as this controller will handle games
    private final GamesService gamesService;
    private final IGDBService igdbService;

    public MongoController(GamesService gamesService, IGDBService igdbService) {
        this.gamesService = gamesService;
        this.igdbService = igdbService;
    }

    //get game from title
    @GetMapping("/get-gft")
    public List<Game> getGamesFromTitle(
            //request title as param (url/get-gft?title=Title to be searched)
            @RequestParam String title
    ){
        //return the list of matches
        return gamesService.getGameByTitle(title);
    }

    //get all games
    @GetMapping("/get-all")
    public List<Game> getAllGames(){
        //return list of matches
        return gamesService.getAllGames();
    }

    //get game by id
    @GetMapping("/get-gid")
    public ResponseEntity<Game> getGameById(
            @RequestParam String id
    ) {
        Game game = gamesService.getGameById(id);
        if(game != null){
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //get id from game name
    @GetMapping("/get-idfn")
    public String getIDofGame(
            @RequestParam String title
    ){
        return gamesService.getGameByTitle(title).get(0).getId();
    }

    //update
    @PostMapping("/update")
    public ResponseEntity<Game> updateGame(
            @RequestBody Game game
    ){
        int res = gamesService.updateGame(game);
        if(res == 1){
            return ResponseEntity.ok().body(game);
        } else {
            return ResponseEntity.badRequest().body(game);
        }
    }

    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteGame(
            @RequestParam String id
    ){
        int res = gamesService.deleteGameById(id);
        if(res == 1){
            return ResponseEntity.ok("Game Deleted");
        } else {
            return ResponseEntity.badRequest().body("Game Not Found");
        }

    }

    //this will just return the exact information from igdb
    @GetMapping("/get-igdb-bt")
    public JSONArray getGameIGDB(
            @RequestParam String name
    ){
        return igdbService.searchGameByTitle(name);
    }

    //pull information from igdb by title/name and save it to db
    //this will save the information from the api by name
    //this can be used with the one above to allow the user to look through games and pick the correct one to be saved
    @GetMapping("/save-igdb-bt")
    public Game saveGameIGDBSave(
            @RequestParam String name
    ){
        return igdbService.saveGameByTitle(name);
    }

    //go through EACH document and check if any fields are null, if they are update the game by title
    @GetMapping("/update-all")
    public void udpateAllGames(){
        igdbService.updateByIGDB();
    }
}
