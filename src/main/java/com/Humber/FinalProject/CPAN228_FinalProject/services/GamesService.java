package com.Humber.FinalProject.CPAN228_FinalProject.services;

import com.Humber.FinalProject.CPAN228_FinalProject.models.Game;
import com.Humber.FinalProject.CPAN228_FinalProject.repositories.primary.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//bean
@Service
public class GamesService {

    //injecting corresponding repo
    private final GameRepository gamesRepository;
    public GamesService(GameRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    //get all
    //return list of Game obj
    public List<Game> getAllGames() {
        //function from MongoRepository extended in GameRepository
        return gamesRepository.findAll();
    }

    //get by id
    //return the specific game as id is unique
    public Game getGameById(String id){
        //custom query
        return gamesRepository.findById(id).orElse(null);
    }

    //get by name
    //return list of games as title is not unique and multiple documents may match
    public List<Game> getGameByTitle(String title){
        return gamesRepository.findByTitle(title);
    }

    //save

    //update

    //delete

}
