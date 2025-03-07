package com.Humber.FinalProject.CPAN228_FinalProject.controllers;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUser;
import com.Humber.FinalProject.CPAN228_FinalProject.services.MyUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    //injecting user service and user games service
    private final MyUserService myUserService;

    public UserController(MyUserService myUserService){
        this.myUserService = myUserService;
    }


    //get user from first name
    @GetMapping("/get-uffn")
    public ResponseEntity<List<MyUser>> getUsersFromFname(
            //request first name as param
            @RequestParam String fname
    ){
        //return list of results
        List<MyUser> results = myUserService.findByFname(fname);
        if (results.isEmpty()){
            return ResponseEntity.badRequest().header("Error", "User not found").body(null);
        }
        return ResponseEntity.ok().body(myUserService.findByFname(fname));
    }

    //get user from id
    @GetMapping("/get-ufid")
    public ResponseEntity<MyUser> getUserFromID(
            @RequestParam String id
    ){
        MyUser results = myUserService.findById(id);
        if (results == null){
            return ResponseEntity.badRequest().header("Error", "Invalid ID").body(null);
        } else {
            return ResponseEntity.ok(results);
        }
    }

    //get user from username
    @GetMapping("/get-ufu")
    public ResponseEntity<MyUser> getUserFromUsername(
            @RequestParam String username
    ){
        MyUser result = myUserService.findByUsername(username);
        if (result == null){
            return ResponseEntity.badRequest().header("Error", "User not found").body(null);
        } else {
            return ResponseEntity.ok(result);
        }


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
        MyUser updatedUser = myUserService.updateUser(myUser);
        if (updatedUser == null){
            return ResponseEntity.badRequest().header("Error", "Invalid ID").body(null);
        } else {
            return ResponseEntity.ok(updatedUser);
        }

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
}
