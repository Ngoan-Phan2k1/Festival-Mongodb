package com.example.festival.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/room")
@CrossOrigin("*")
public class RoomResource {

    @Autowired
    private RoomService roomService;

    @GetMapping("/{hotel_id}")
    public ResponseEntity<List<RoomDTO>> findByHotelId(
        @PathVariable String hotel_id
    ) {
        List<RoomDTO> roomDTOs = roomService.findByHotelId(hotel_id);
        return ResponseEntity.status(HttpStatus.OK).body(roomDTOs);
    }

    @PostMapping
    public ResponseEntity<RoomDTO> add(
        @RequestBody @Valid Room room,
        @RequestParam(value = "imageName", required = false) String imageName
        //@RequestParam(value = "hotel_id", required = true) Integer hotel_id
        ) {
    
        RoomDTO roomDTO = roomService.add(room, imageName);
        return ResponseEntity.status(HttpStatus.OK).body(roomDTO);
    }

    @GetMapping("find_room/{room_id}")
    public ResponseEntity<RoomDTO> findById(
        @PathVariable String room_id
    ) {
        RoomDTO roomDTO = roomService.findById(room_id);
        return ResponseEntity.status(HttpStatus.OK).body(roomDTO);
    }
    
    
}
