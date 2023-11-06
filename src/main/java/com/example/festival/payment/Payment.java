package com.example.festival.payment;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.festival.booktour.BookedTour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "payment")
public class Payment {

    @Id
    private String id;

    @NonNull
    private Integer amount;

    @NonNull
    private String vnp_TxnRef;

    private LocalDateTime dateOfCheckout;

    @NonNull
    private BookedTour bookedTour;
    
}
