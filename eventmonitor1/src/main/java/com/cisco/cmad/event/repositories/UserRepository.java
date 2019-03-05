/**
 * 
 */
package com.cisco.cmad.event.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cisco.cmad.event.mongo.dao.Users;


/**
 * @author tcheedel
 *
 */
public interface UserRepository extends MongoRepository<Users, String> {
    
    @Query("{ 'name' : ?0 }")
    List<Users> findUserByName(String name);
    
    @Query("{ 'name' : ?0, 'password': ?1 }")
    List<Users> findUserForLogin(String name, String password);

}
