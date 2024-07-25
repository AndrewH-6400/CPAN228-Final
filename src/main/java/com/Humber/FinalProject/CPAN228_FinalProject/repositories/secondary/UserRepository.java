package com.Humber.FinalProject.CPAN228_FinalProject.repositories.secondary;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<MyUser, String> {

    @Query("{ 'fname' : ?0 }")
    public List<MyUser> findByFname(String fname);
}
