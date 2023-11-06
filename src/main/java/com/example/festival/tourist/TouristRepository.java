package com.example.festival.tourist;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristRepository extends MongoRepository<Tourist, String> {
    
    @Query("{ 'user.username' : ?0 }")
    Tourist findByUsername(String username);
}
