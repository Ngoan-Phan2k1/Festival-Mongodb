package com.example.festival.tourist;

import org.springframework.data.annotation.Id;

import com.example.festival.image.Image;
import com.example.festival.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tourist {

    @Id
    private String id;

    private String fullname;
    private String email;
    private Image image;

    private User user;
}
