package com.example.festival.festival;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "festival")
public class Festival {

    @Id // Đánh dấu đây là ID
    private String id;

    private String name;
    private String description;
    private LocalDate fromDate;
    private LocalDate toDate;

    @AssertTrue(message = "fromDate must be before toDate")
    private boolean isFromBeforeTo() {
        // Kiểm tra nếu fromDate không lớn hơn toDate thì trả về true, ngược lại trả về false
        return fromDate == null || toDate == null || fromDate.isBefore(toDate);
    }
    
}
