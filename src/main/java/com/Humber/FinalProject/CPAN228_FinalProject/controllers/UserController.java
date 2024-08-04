package com.Humber.FinalProject.CPAN228_FinalProject.controllers;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUser;
import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUserGames;
import com.Humber.FinalProject.CPAN228_FinalProject.services.MyUserGamesService;
import com.Humber.FinalProject.CPAN228_FinalProject.services.MyUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    //injecting user service and user games service
    private final MyUserService myUserService;
    private final MyUserGamesService myUserGamesService;

    public UserController(MyUserService myUserService, MyUserGamesService myUserGamesService){
        this.myUserService = myUserService;
        this.myUserGamesService = myUserGamesService;
    }


    //get user from first name
    @GetMapping("/get-uffn")
    public ResponseEntity<List<MyUser>> getUsersFromFname(
            //request first name as param
            @RequestParam String fname
    ){
        //return list of results
        return ResponseEntity.ok().body(myUserService.findByFname(fname));
    }

    //get user from id
    @GetMapping("/get-ufid")
    public ResponseEntity<MyUser> getUserFromID(
            @RequestParam String id
    ){
        return ResponseEntity.ok(myUserService.findById(id));
    }

    //get all users
    @GetMapping("/get-all")
    public List<MyUser> getAllUsers(){
        return myUserService.getAll();
    }

    //Save user
    @PostMapping("/save-user")
    public ResponseEntity<String> saveUser(
            @RequestBody MyUser myUser
    ){
        myUserService.saveUser(myUser);
        return ResponseEntity.ok("User Saved");
    }

    //Update User
    @PostMapping("/update-user")
    public ResponseEntity<MyUser> updateUser(
            @RequestBody MyUser myUser
    ){
        return ResponseEntity.ok(myUserService.updateUser(myUser));
    }

    //delete user
    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(
            @RequestParam String id
    ){
        int res = myUserService.deleteUser(id);
        if(res == 1){
            return ResponseEntity.ok("User Deleted");
        } else {
            return ResponseEntity.ok("User Not Found");
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
        myUserGamesService.saveUG(ug);
        return ResponseEntity.ok(ug);
    }
}
