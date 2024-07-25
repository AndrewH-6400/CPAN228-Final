package com.Humber.FinalProject.CPAN228_FinalProject.controllers;

import com.Humber.FinalProject.CPAN228_FinalProject.models.Game;
import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUser;
import com.Humber.FinalProject.CPAN228_FinalProject.services.GamesService;
import com.Humber.FinalProject.CPAN228_FinalProject.services.MyUserService;
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

    public MongoController(GamesService gamesService, MyUserService myUserService) {
        this.gamesService = gamesService;
        this.myUserService = myUserService;
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

    //get user from fname
    @GetMapping("/get-uffn")
    public List<MyUser> getUsersFromFname(
            //request first name as param
            @RequestParam String fname
    ){
        //return list of results
        return myUserService.findByFname(fname);
    }

}
