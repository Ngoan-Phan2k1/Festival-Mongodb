package com.example.festival.tourist;

import com.example.festival.image.ImageDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TouristDTO {

    private String id;
    private String fullname;
    private String username;
    private String email;
    private ImageDTO imageDTO;
    
}
