package com.example.festival.tour;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tour")
@CrossOrigin("*")
public class TourResource {

    @Autowired
    private TourService tourService;

    @PostMapping
    public ResponseEntity<TourDTO> add(
        @RequestBody @Valid Tour tour,
        @RequestParam(value = "imageName", required = false) String imageName
    ) {

        TourDTO tourDTO = tourService.add(tour, imageName);
        return ResponseEntity.status(HttpStatus.OK).body(tourDTO);
    }

    @GetMapping
    public ResponseEntity<List<TourDTO>> findAll() {

        List<TourDTO> tourDTOs = tourService.findTours();
        return ResponseEntity.status(HttpStatus.OK).body(tourDTOs);
    }
}
