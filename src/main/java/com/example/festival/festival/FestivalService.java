package com.example.festival.festival;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FestivalService {
    
    @Autowired
    private FestivalRepositoty festivalRepositoty;

    public Festival add(Festival festival) {
        return festivalRepositoty.save(festival);
    }

    public Optional<Festival> findById(String id) {
        
       Optional<Festival> festival = festivalRepositoty.findById(id);
       if (!festival.isPresent()) {
        
       }
        return festival;
    }

    // public List<Festival> findByDateRange(LocalDate fromDate, LocalDate toDate) {
    //     return festivalRepositoty.findFestivalsWithinDateRange(fromDate, toDate);
    // }
}

