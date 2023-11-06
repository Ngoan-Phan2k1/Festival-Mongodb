package com.example.festival.room;

import java.util.List;

import com.example.festival.hotel.HotelDTO;
import com.example.festival.image.ImageDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    
    private String id;
    private String hotel_id;
    private String name;
    private Integer price;
    private List<String> services;
    private ImageDTO imageDTO;
    private HotelDTO hotelDTO;
}
