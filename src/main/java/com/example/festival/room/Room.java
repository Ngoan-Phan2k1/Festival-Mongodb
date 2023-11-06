package com.example.festival.room;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.festival.hotel.Hotel;
import com.example.festival.image.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "room")
public class Room {
    
    @Id
    private String id;

    private String name;
    private Integer price;
    private List<String> services;
    private Image image;
    private Hotel hotel;
}
