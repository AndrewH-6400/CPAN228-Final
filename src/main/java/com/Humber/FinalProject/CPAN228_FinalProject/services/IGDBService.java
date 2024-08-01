package com.Humber.FinalProject.CPAN228_FinalProject.services;

import com.Humber.FinalProject.CPAN228_FinalProject.models.Game;
import com.Humber.FinalProject.CPAN228_FinalProject.repositories.CustomIGDBRepository;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class IGDBService {
    private final CustomIGDBRepository customIGDBRepository;

    public IGDBService(CustomIGDBRepository customIGDBRepository) {
        this.customIGDBRepository = customIGDBRepository;
    }

    //search game by title
    //not working lol, but i don't even know if this is worth using
    public JSONArray searchGameByTitle(String title){
        //set body which is the formatting for the request
        String body = "fields name,summary,involved_companies,genres,themes; where name = "+'"'+title+'"'+";";
        return customIGDBRepository.searchIGDB(body,"/games");
    }

    //save game from igdb by title
    public Game saveGameByTitle(String title){
        return customIGDBRepository.saveIGDBbyTitle(title);
    }
}
