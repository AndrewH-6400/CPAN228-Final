package com.Humber.FinalProject.CPAN228_FinalProject.controllers;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUserGames;
import com.Humber.FinalProject.CPAN228_FinalProject.services.MyUserGamesService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-games")
public class UserGameController {

    private final MyUserGamesService myUserGamesService;

    public UserGameController(MyUserGamesService myUserGamesService) {
        this.myUserGamesService = myUserGamesService;
    }

    //get all
    @GetMapping("/get-all")
    public ResponseEntity<List<MyUserGames>> getAllUserGames() {
        return ResponseEntity.ok(myUserGamesService.getAllUserGames());
    }

    //get by id
    @GetMapping("/get-bid")
    public ResponseEntity<MyUserGames> getUserGameById(
            @RequestParam String userGameId
    ) {
        MyUserGames res = myUserGamesService.getById(userGameId);
        if(res != null){
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //get by userid and gameid
    @GetMapping("/get-uidgid")
    public ResponseEntity<MyUserGames> getUserGameByUserIdGameId(
            @RequestParam String userId,
            @RequestParam String gameId
    ) {
        MyUserGames res = myUserGamesService.getByGameIdUserId(userId, gameId);
        if(res != null){
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //get all by userid
    @GetMapping("/get-aug")
    public ResponseEntity<List<MyUserGames>> getUserGameByUserId(
            @RequestParam String userId
    ) {
        List<MyUserGames> userGames = myUserGamesService.getAllUsersGames(userId);
        if(userGames != null){
            return ResponseEntity.ok(userGames);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //save user game (ug)
    //these are instances of a users entries to a game
    //will hold user specific information related to the game
    //as well as a reference to the game and the user
    @PostMapping("/save-ug")
    public ResponseEntity<MyUserGames> saveUG(
            @RequestBody MyUserGames ug
    ){
        ug.setId(new ObjectId().toString());
        myUserGamesService.saveUG(ug);
        return ResponseEntity.ok(ug);
    }

    //update
    @PostMapping("/update-ug")
    public ResponseEntity<MyUserGames> updateUG(
            @RequestBody MyUserGames ug
    ){
        if (myUserGamesService.updateUG(ug) != null){
            return ResponseEntity.ok(ug);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //delete
    @DeleteMapping("/delete-ug")
    public ResponseEntity<MyUserGames> deleteUG(
            @RequestParam String userGameId
    ) {
        if(myUserGamesService.deleteUG(userGameId) == 1){
            return ResponseEntity.ok(myUserGamesService.getById(userGameId));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
