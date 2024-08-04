package com.Humber.FinalProject.CPAN228_FinalProject.services;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUserGames;
import com.Humber.FinalProject.CPAN228_FinalProject.repositories.secondary.UserGamesRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserGamesService {
    //injecting user games repository
    private final UserGamesRepository userGamesRepository;

    public MyUserGamesService(UserGamesRepository userGamesRepository){
        this.userGamesRepository = userGamesRepository;
    }

    //get all
    public List<MyUserGames> getAllUserGames(){
        return userGamesRepository.findAll();
    }

    //get by id
    public MyUserGames getById(String id){
        return userGamesRepository.findById(id).orElse(null);
    }

    //get by userid and game
    public MyUserGames getByGameIdUserId(String userId, String gameId){
        //find by userid and gameid return null if not found
        return(userGamesRepository.findByUserIdGameId(userId,gameId).orElse(null));
    }

    //get all a users games
    public List<MyUserGames> getAllUsersGames(String userId){
        return(userGamesRepository.findAllByUserId(userId).orElse(null));
    }

    //save
    public MyUserGames saveUG(MyUserGames ug){
        //generate object id
        if(userGamesRepository.existsById(ug.getId())){
            return null;
        }
        ug.setId(new ObjectId().toString());
        userGamesRepository.save(ug);
        return ug;
    }

    //update
    public MyUserGames updateUG(MyUserGames ug){
        if(getById(ug.getId()) != null){
            userGamesRepository.save(ug);
            return ug;
        } else {
            return null;
        }


    }

    //delete
    public int deleteUG(String id){
        if (userGamesRepository.existsById(id)){
            userGamesRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }
}
