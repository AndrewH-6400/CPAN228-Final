package com.Humber.FinalProject.CPAN228_FinalProject.services;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUser;
import com.Humber.FinalProject.CPAN228_FinalProject.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserService {

    private final UserRepository userRepository;
    public MyUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //find by name
    public List<MyUser> findByFname(String fname) {
        return userRepository.findByFname(fname);
    }
}
