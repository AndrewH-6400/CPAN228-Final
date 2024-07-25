package com.Humber.FinalProject.CPAN228_FinalProject.services;

import com.Humber.FinalProject.CPAN228_FinalProject.models.Game;
import com.Humber.FinalProject.CPAN228_FinalProject.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamesService {

    private final GameRepository gamesRepository;
    public GamesService(GameRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    //get all
    public List<Game> getAllGames() {
        return gamesRepository.findAll();
    }

    //get by id
    public Game getGameById(String id){
        return gamesRepository.findById(id).orElse(null);
    }

    //get by name
    public List<Game> getGameByTitle(String title){
        return gamesRepository.findByTitle(title);
    }

    //save

    //update

    //delete

}
