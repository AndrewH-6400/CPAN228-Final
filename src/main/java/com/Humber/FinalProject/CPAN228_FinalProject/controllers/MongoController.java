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

//rest controller as we are only operating as a service for database integration
@RestController
//map requests through Database
@RequestMapping("/Database")
public class MongoController {

    //injecting games and user services
    private final GamesService gamesService;
    private final MyUserService myUserService;
    private final IGDBService igdbService;

    public MongoController(GamesService gamesService, MyUserService myUserService, IGDBService igdbService) {
        this.gamesService = gamesService;
        this.myUserService = myUserService;
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

    //get id from game name
    @GetMapping("/get-idfn")
    public String getIDofGame(
            @RequestParam String title
    ){
        return gamesService.getGameByTitle(title).get(0).getId().toString();
    }

    //update
    //spring security is stopping POST requests from going through
    @PostMapping("/update")
    public ResponseEntity<Game> updateGame(
            @RequestBody Game game
    ){
        gamesService.updateGame(game);
        return ResponseEntity.ok().body(game);
    }

    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteGame(
            @RequestParam String id
    ){
        System.out.println(id);
        gamesService.deleteGameById(id);
        return ResponseEntity.ok("Game Deleted");
    }

    @GetMapping("/get-igdb-bt")
    public JSONArray getGameIGDB(
            @RequestParam String name
    ){
        return igdbService.searchGameByTitle(name);
    }

    //pull information from igdb by title/name and save it to db
    @GetMapping("/save-igdb-bt")
    public Game saveGameIGDBSave(
            @RequestParam String name
    ){
        return igdbService.saveGameByTitle(name);
    }
}
