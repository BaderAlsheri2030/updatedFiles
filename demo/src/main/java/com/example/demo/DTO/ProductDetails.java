package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDetails {

    private Integer product_id;
    @NotNull(message = "quantity cannot be null")
    @Positive(message = "quantity must be number")
    private Integer quantity;

}
