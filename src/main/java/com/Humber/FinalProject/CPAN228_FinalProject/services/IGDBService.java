package com.Humber.FinalProject.CPAN228_FinalProject.services;

import com.Humber.FinalProject.CPAN228_FinalProject.models.Game;
import com.Humber.FinalProject.CPAN228_FinalProject.repositories.CustomIGDBRepository;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class IGDBService {
    //inject IGDB repo (idk if repo is the best term for this but it is what I chose)
    private final CustomIGDBRepository customIGDBRepository;

    public IGDBService(CustomIGDBRepository customIGDBRepository) {
        this.customIGDBRepository = customIGDBRepository;
    }

    //search game by title
    public JSONArray searchGameByTitle(String title){
        //set body which is the formatting for the request
        String body = "fields name,summary,involved_companies,genres,themes; where name = "+'"'+title+'"'+";";
        //pass the correct route as well
        return customIGDBRepository.searchIGDB(body,"/games");
    }

    //save game from igdb by title
    public Game saveGameByTitle(String title){
        return customIGDBRepository.saveIGDBbyTitle(title);
    }
}
