package com.example.festival.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    
    private String token;
    private String touristId;
    private String username;
    private String fullname;
    private String email;
    private Long tokenExpirationDate;
}
