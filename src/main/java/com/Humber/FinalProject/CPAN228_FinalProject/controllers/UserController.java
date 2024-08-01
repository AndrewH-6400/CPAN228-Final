package com.Humber.FinalProject.CPAN228_FinalProject.controllers;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUser;
import com.Humber.FinalProject.CPAN228_FinalProject.services.MyUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final MyUserService myUserService;

    public UserController(MyUserService myUserService){
        this.myUserService = myUserService;
    }


    //get user from fname
    @GetMapping("/get-uffn")
    public ResponseEntity<List<MyUser>> getUsersFromFname(
            //request first name as param
            @RequestParam String fname
    ){
        //return list of results
        return ResponseEntity.ok().body(myUserService.findByFname(fname));
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

}
