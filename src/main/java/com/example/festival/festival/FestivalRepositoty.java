package com.example.festival.festival;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.festival.tour.Tour;


@Repository
public interface FestivalRepositoty extends MongoRepository<Festival, String> {
    
   
    // Optional<Festival> findByName(String name);

    // @Query("SELECT f FROM Festival f WHERE f.fromDate >= :fromDate AND f.toDate <= :toDate")
    // List<Festival> findFestivalsWithinDateRange(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
