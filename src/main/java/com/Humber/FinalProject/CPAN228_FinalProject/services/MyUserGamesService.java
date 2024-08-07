package com.Humber.FinalProject.CPAN228_FinalProject.services;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUser;
import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUserGames;
import com.Humber.FinalProject.CPAN228_FinalProject.repositories.secondary.UserGamesRepository;
import com.Humber.FinalProject.CPAN228_FinalProject.repositories.secondary.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserGamesService {
    //injecting user games repository
    private final UserGamesRepository userGamesRepository;
    private final UserRepository userRepository;
    public MyUserGamesService(UserGamesRepository userGamesRepository, UserRepository userRepository){
        this.userGamesRepository = userGamesRepository;
        this.userRepository = userRepository;
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
        if(userRepository.existsById(ug.getUserId())){
            //generate usergame id
            ug.setId(new ObjectId().toString());
            //find user
            MyUser user = userRepository.findById(ug.getUserId()).orElse(null);

            //create game list, if user already has one set it equal to that
            List<String> gamelist = new ArrayList<>();
            if(user.getMyUserGamesIds() != null){
                gamelist = user.getMyUserGamesIds();
            }
            //add the usergame id
            gamelist.add(ug.getGameId());
            //set it
            user.setMyUserGamesIds(gamelist);
            //save both user and usergame
            userRepository.save(user);
            userGamesRepository.save(ug);
            return ug;
        } else {
            return null;
        }

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
