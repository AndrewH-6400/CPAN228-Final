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

    //save
    public int saveGame(Game game){
        //this check can be edited to allow for same name games
        // 0 for id exists, -1 for name exists. 1 for success
        if(gamesRepository.existsById(game.getId())){
            System.out.println("found id");
            return 0;
        } else if (!gamesRepository.findByTitle(game.getTitle()).isEmpty()) {
            System.out.println("found name");
            return -1;
        }
        System.out.println("made it to save game");
        gamesRepository.save(game);
        return 1;

    }
    //update
    public int updateGame(Game game){
        //this will need to change from being name based to id based
        //this will be easier when the id can be pulled from the website
        if(getGameById(game.getId()) != null){
            gamesRepository.save(game);
            return 1;
        } else {
            return 0;
        }
    }

    //delete
    public int deleteGameById(String id){
        //success = 1
        gamesRepository.deleteById(id);
        return 1;
    }

    //search by title
    public List<Game> searchGame(String title){
        return gamesRepository.findByTitleContaining(title);
    }
}
