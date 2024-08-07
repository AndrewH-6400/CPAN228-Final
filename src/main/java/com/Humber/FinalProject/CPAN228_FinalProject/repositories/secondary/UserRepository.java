package com.Humber.FinalProject.CPAN228_FinalProject.repositories.secondary;

import com.Humber.FinalProject.CPAN228_FinalProject.models.MyUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//makes a bean
@Repository
//UserRepository extends MongoRepository to associate the MyUser class
//secondary config sets the cluster connection, and the database then associates those with this repository
//while the class MyUser through @Document states the collection it belongs to
public interface UserRepository extends MongoRepository<MyUser, String> {

    //search for a first name
    public List<MyUser> findByFnameContaining(String fname);
}
