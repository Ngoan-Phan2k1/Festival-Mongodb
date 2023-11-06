package com.example.festival.booktour;

import java.time.LocalDateTime;

import com.example.festival.room.RoomDTO;
import com.example.festival.tour.TourDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookedTourDTO {

    private String booked_id;
    private Integer bookedAdult;
    private Integer bookedChild;
    private Integer bookedBaby;

    private String fullname;
    private String email;
    private String address;
    private String note;
    private Integer num_room;
    private String phone;
    
    private boolean isCheckout;
    private LocalDateTime dateOfBooking;
    private TourDTO tourDto;
    private RoomDTO roomDtO;
    
}
