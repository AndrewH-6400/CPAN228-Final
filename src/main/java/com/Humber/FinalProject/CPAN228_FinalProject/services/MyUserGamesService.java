package com.Humber.FinalProject.CPAN228_FinalProject.services;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUserGames;
import com.Humber.FinalProject.CPAN228_FinalProject.repositories.secondary.UserGamesRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class MyUserGamesService {
    //injecting user games repository
    private final UserGamesRepository userGamesRepository;

    public MyUserGamesService(UserGamesRepository userGamesRepository){
        this.userGamesRepository = userGamesRepository;
    }

    //save
    public MyUserGames saveUG(MyUserGames ug){
        //generate object id
        ug.setId(new ObjectId().toString());
        userGamesRepository.save(ug);
        return ug;
    }
}
