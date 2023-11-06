package com.example.festival.hotel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.festival.festival.Festival;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {
    
    Boolean existsByName(String name);
}
