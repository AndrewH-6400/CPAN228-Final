package com.Humber.FinalProject.CPAN228_FinalProject.repositories.secondary;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUserGames;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGamesRepository extends MongoRepository<MyUserGames, String> {

    //custom query to find by userid and gameid
    @Query("{$and: [{'userId' : ?0},{'gameId': ?1}]}")
    public Optional<MyUserGames> findByUserIdGameId(String userId, String gameId);

    //custom query to find all a users Games
    public Optional<List<MyUserGames>> findAllByUserId(String userId);
}
