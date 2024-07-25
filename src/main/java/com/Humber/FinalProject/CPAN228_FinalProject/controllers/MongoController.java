package com.Humber.FinalProject.CPAN228_FinalProject.controllers;

import com.Humber.FinalProject.CPAN228_FinalProject.models.Game;
import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUser;
import com.Humber.FinalProject.CPAN228_FinalProject.services.GamesService;
import com.Humber.FinalProject.CPAN228_FinalProject.services.MyUserService;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@RestController
@RequestMapping("/Database")
public class MongoController {

    private final GamesService gamesService;
    private final MyUserService myUserService;

    public MongoController(GamesService gamesService, MyUserService myUserService) {

        this.gamesService = gamesService;
        this.myUserService = myUserService;

    }

    //get game from title
    @GetMapping("/get-gft")
    public List<Game> getGamesFromTitle(@RequestParam String title){
        return gamesService.getGameByTitle(title);
    }

    //get all games
    @GetMapping("/get-all")
    public List<Game> getAllGames(){
        return gamesService.getAllGames();
    }

    //get user from fname
    @GetMapping("/get-uffn")
    public List<MyUser> getUsersFromFname(@RequestParam String fname){
        return myUserService.findByFname(fname);
    }

}
