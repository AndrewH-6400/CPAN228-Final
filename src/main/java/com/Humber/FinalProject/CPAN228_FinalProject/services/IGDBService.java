package com.Humber.FinalProject.CPAN228_FinalProject.services;

import com.Humber.FinalProject.CPAN228_FinalProject.models.Game;
import com.Humber.FinalProject.CPAN228_FinalProject.repositories.CustomIGDBRepository;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class IGDBService {
    //inject IGDB repo (idk if repo is the best term for this but it is what I chose)
    private final CustomIGDBRepository customIGDBRepository;
    private final GamesService gamesService;

    public IGDBService(CustomIGDBRepository customIGDBRepository, GamesService gamesService) {
        this.customIGDBRepository = customIGDBRepository;
        this.gamesService = gamesService;
    }

    //search game by title
    public JSONArray searchGameByTitle(String title){
        //set body which is the formatting for the request
        String body = "fields name,summary,involved_companies,genres,themes,cover; where name = "+'"'+title+'"'+";";
        //pass the correct route as well
        return customIGDBRepository.searchIGDB(body,"/games");
    }

    //save game from igdb by title
    public Game saveGameByTitle(String title){
        return customIGDBRepository.saveIGDBbyTitle(title, null);
    }

    //update database
    public void updateByIGDB(){
        System.out.println("Updating database\n");
        int nullFields = 0;
        for (Game g : gamesService.getAllGames()){
            ArrayList<String> fields = new ArrayList<>();
            if (g.getGenres() == null){
                nullFields +=1;
                fields.add("genre");
            } else if (g.getTitle() == null) {
                nullFields +=1;
                fields.add("title");
            } else if(g.getCover() == null) {
                nullFields +=1;
                fields.add("cover");
            } else if(g.getDeveloper() == null){
                nullFields +=1;
                fields.add("developer");
            } else if(g.getPublisher() == null){
                nullFields +=1;
                fields.add("publisher");
            } else if(g.getThemes() == null){
                nullFields +=1;
                fields.add("theme");
            } else if(g.getDescription() == null){
                nullFields +=1;
                fields.add("description");
            }

            if(nullFields != 0){
                System.out.println("Updating: " + g.getTitle());
                System.out.println("Fields updated: " + fields+"\n");
                customIGDBRepository.saveIGDBbyTitle(g.getTitle(),g.getId());
            }

        }
        System.out.println("Update complete");
        System.out.println("Total Fields Updated: " + nullFields);
    }
}
