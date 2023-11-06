package com.example.festival.booktour;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.festival.room.Room;
import com.example.festival.tour.Tour;
import com.example.festival.tourist.Tourist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "booked_tour")
public class BookedTour {
    
    @Id
    private String id;

    private Integer bookedAdult;
    private Integer bookedChild;
    private Integer bookedBaby;
    private String fullname;
    private String email;
    private String address;
    private String note;
    private Integer numRoom;
    private String phone;
    private Boolean isCheckout;
    private LocalDateTime dateOfBooking;

    private Room room;
    private Tour tour;
    private Tourist tourist;
}
