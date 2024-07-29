package com.Humber.FinalProject.CPAN228_FinalProject.services;

import com.Humber.FinalProject.CPAN228_FinalProject.repositories.CustomIGDBRepository;
import org.springframework.stereotype.Service;

@Service
public class IGDBService {
    private final CustomIGDBRepository customIGDBRepository;

    public IGDBService(CustomIGDBRepository customIGDBRepository) {
        this.customIGDBRepository = customIGDBRepository;
    }

    //search game by title
    public void searchGameByTitle(String title){
        //add logic here, possible to make sure that we get no errors, format title etc...
        customIGDBRepository.searchIGDBbyTitle(title);
    }

    //save game from igdb by title
    public void saveGameByTitle(String title){
        customIGDBRepository.saveIGDBbyTitle(title);
    }
}
