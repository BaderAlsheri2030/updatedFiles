package com.example.demo.DTO;

import com.example.demo.Model.Provider;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class OfferDTO {
    private LocalDate dead_line;
    private double price;
    private Integer provider_id;
    private Integer rfp_id;
    private String conditions;
    private String description;
    @Pattern(regexp = "^(accepted|rejected|pending)$")
    private String status;

}