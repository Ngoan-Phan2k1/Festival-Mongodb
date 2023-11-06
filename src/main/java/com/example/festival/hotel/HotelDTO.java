package com.example.festival.hotel;

import java.util.List;

import com.example.festival.image.ImageDTO;
import com.example.festival.tour.Tour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {

    private String id;
    private String name;
    private String location;
    private String introduce;
    private List<String> services;
    private ImageDTO imageDTO;

}
