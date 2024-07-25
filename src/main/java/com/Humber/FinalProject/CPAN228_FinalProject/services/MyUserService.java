package com.Humber.FinalProject.CPAN228_FinalProject.services;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUser;
import com.Humber.FinalProject.CPAN228_FinalProject.repositories.secondary.UserRepository;
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
}
