package com.example.festival.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private String amount;
    private String bankCode;
    private String order;
    private String responseCode;
    
}
