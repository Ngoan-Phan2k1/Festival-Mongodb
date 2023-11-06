package com.example.festival.tour;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.festival.festival.Festival;
import com.example.festival.hotel.Hotel;
import com.example.festival.image.Image;

import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "tour")
public class Tour {
    
    @Id
    private String id;

    private String name;
    private String fromWhere;
    private String toWhere;
    private String description;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer priceAdult;
    private Integer priceChild;
    private Integer priceBaby;
    private Integer capacity;
    private Integer booked;

    private Festival festival;
    private Image image;
    private List<Hotel> hotels;

    @AssertTrue(message = "fromDate must be before toDate")
    public boolean isFromBeforeTo() {
        return fromDate == null || toDate == null || fromDate.isBefore(toDate);
    }

}
