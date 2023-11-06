package com.example.festival.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/hotel")
@CrossOrigin("*")
public class HotelResource {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDTO> add(
        @RequestBody @Valid Hotel hotel,
        @RequestParam(value = "imageName", required = false) String imageName
    ) {

        HotelDTO hotelDB = hotelService.add(hotel, imageName);
        return ResponseEntity.status(HttpStatus.OK).body(hotelDB);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDTO> findById(
        @PathVariable String hotelId
    ) {

        HotelDTO hotelDTO = hotelService.findById(hotelId);
        if (hotelDTO == null) {
            //throw new NotFoundException("Không tìm thấy khách sạn");
        }
        return ResponseEntity.status(HttpStatus.OK).body(hotelDTO);
    }
}
