package com.Humber.FinalProject.CPAN228_FinalProject.controllers;

import com.Humber.FinalProject.CPAN228_FinalProject.models.Game;
import com.Humber.FinalProject.CPAN228_FinalProject.services.GamesService;
import com.Humber.FinalProject.CPAN228_FinalProject.services.IGDBService;
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

    //true search
    //use to show the admin the list of games they can choose from
    //save function is specific to the name of the game, so you must input the proper name
    @GetMapping("/search-igdb")
    public ResponseEntity<String> searchGames(@RequestParam String search){

        String results = igdbService.trueSearch(search).toString();
        if(results.isEmpty()){
            return ResponseEntity.badRequest().body("No results found");
        }
        return ResponseEntity.ok().body(results);
    }

    //get all games
    @GetMapping("/get-all")
    public ResponseEntity<List<Game>> getAllGames(){
        //return list of matches
        List<Game> games = gamesService.getAllGames();
        if(games.isEmpty()){
            return ResponseEntity.badRequest().header("Error","Error Finding Games").body(null);
        } else {
            return ResponseEntity.ok().body(games);
        }
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
            return ResponseEntity.badRequest().header("Error","Invalid Game ID").body(null);
        }
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
            return ResponseEntity.badRequest().header("Error","Invalid Game ID").body(null);
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
    public ResponseEntity<String> getGameIGDB(
            @RequestParam String name
    ){
        String results = igdbService.searchGameByTitle(name).toString();
        if(results.isEmpty()){
            return ResponseEntity.badRequest().body("Game Not Found");
        } else {
            return ResponseEntity.ok(results);
        }
    }

    //pull information from igdb by title/name and save it to db
    //this will save the information from the api by name
    //this can be used with the one above to allow the user to look through games and pick the correct one to be saved
    @GetMapping("/save-igdb-bt")
    public ResponseEntity<Game> saveGameIGDBSave(
            @RequestParam String name
    ){
        return ResponseEntity.ok(igdbService.saveGameByTitle(name));
    }

    //go through EACH document and check if any fields are null, if they are update the game by title
    //has no output as this will only output things for the terminal on the server end
    @GetMapping("/update-all")
    public void updateAllGames(){
        igdbService.updateByIGDB();
    }
}
