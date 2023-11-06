package com.example.festival.booktour;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedTourRepository extends MongoRepository<BookedTour, String> {

    @Query("{ 'tour.id' : ?0, 'tourist.id' : ?1 }")
    Optional<BookedTour> findByTourIdAndTouristId(String tourId, String touristId);
    
    @Query("{'tourist.id' : ?0}")
    List<BookedTour> findAllByTouristId(Integer touristId);

}
