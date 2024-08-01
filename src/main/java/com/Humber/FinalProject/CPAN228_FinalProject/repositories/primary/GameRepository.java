package com.Humber.FinalProject.CPAN228_FinalProject.repositories.primary;

import com.Humber.FinalProject.CPAN228_FinalProject.models.Game;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//make a bean
@Repository
//extends MongoRepository, associating the Game class with GameRepository
//Primary config associates itself with this repo and tells it and its associated classes(Game) to use primaryMongoTemplate
//primaryMongoTemplate is configured in the PrimaryConfig to use the Games database
//and the model Game is already associated with the collection Games giving us our through line of
//cluster(in config) -> database(in config) -> collection(in model) -> document(queried here)
public interface GameRepository extends MongoRepository<Game, String> {

//  Query to find by title
    @Query("{ 'title' : ?0 }")
    List<Game> findByTitle(String title);
}
