package com.example.festival.tour;

import java.time.LocalDate;
import java.util.List;

import com.example.festival.festival.Festival;
import com.example.festival.hotel.HotelDTO;
import com.example.festival.image.ImageDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDTO {
    private String id;
    //private Integer festival_id;
    private String name;
    private String fromWhere;
    private String toWhere;
    private String description;
    private LocalDate fromDate; // Ngày khởi hành
    private LocalDate toDate;   // Ngày về
    private Integer priceAdult;
    private Integer priceChild;
    private Integer priceBaby;
    private Integer capacity;
    private Integer booked;
    private Festival festival;
    private ImageDTO imageDTO;
    List<HotelDTO> hotelDTOs;
    
}
