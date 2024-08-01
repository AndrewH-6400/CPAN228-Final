package com.Humber.FinalProject.CPAN228_FinalProject.repositories.secondary;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUserGames;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGamesRepository extends MongoRepository<MyUserGames, String> {
}
