package com.Humber.FinalProject.CPAN228_FinalProject.services;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUser;
import com.Humber.FinalProject.CPAN228_FinalProject.repositories.secondary.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

//bean
@Service
public class MyUserService {

    //inject corresponding repository
    private final UserRepository userRepository;
    public MyUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //find by name
    //return list as fist name is not unique
    public List<MyUser> findByFname(String fname) {
        //uses custom query
        return userRepository.findByFname(fname);
    }

    public MyUser findById(String id){
        return userRepository.findById(id).orElse(null);
    }

    public List<MyUser> getAll(){
        return userRepository.findAll();
    }

    //no encryption here to let the front end handle encryption
    public MyUser saveUser(MyUser myUser){
        myUser.setId(new ObjectId().toString());
        userRepository.save(myUser);
        return myUser;
    }

    //update user
    public MyUser updateUser(MyUser myUser){

        System.out.println(myUser.getId());
        MyUser tempID = userRepository.findById(myUser.getId()).orElse(null);
        if (tempID != null) {
            myUser.setId(tempID.getId());
            userRepository.save(myUser);
            System.out.println("saved");
            return myUser;
        }
        return  myUser;
    }
}
