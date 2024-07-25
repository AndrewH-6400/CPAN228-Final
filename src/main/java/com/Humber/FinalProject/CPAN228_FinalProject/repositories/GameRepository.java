package com.Humber.FinalProject.CPAN228_FinalProject.repositories;

import com.Humber.FinalProject.CPAN228_FinalProject.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {

//  Query to find by title
    @Query("{ 'title' : ?0 }")
    List<Game> findByTitle(String title);
}
