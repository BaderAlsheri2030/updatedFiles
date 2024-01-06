package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {

    private Integer provider_id;
    @NotNull(message = "price cannot be null")
    private String name;
    @Positive(message = "price must be number")
    @NotNull(message = "price cannot be null")
    private double price;
}
