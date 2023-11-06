package com.example.festival.hotel;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.festival.image.Image;
import com.example.festival.tour.Tour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "hotel")
public class Hotel {

    @Id // Đánh dấu đây là ID
    private String id;
    private String name;
    private String location;
    private String introduce;
    private List<String> services;
    private List<Tour> tours;
    private Image image;
}
