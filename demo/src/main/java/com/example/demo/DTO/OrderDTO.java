package com.example.demo.DTO;


import com.example.demo.Model.Product;
import com.example.demo.Model.Service;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class OrderDTO {
    @Pattern(regexp = "^(accepted|rejected|completed)$")
    private String status;
    private Integer quantity;
    private double totalPrice;
    private Integer product_id;
    private Integer service_id;
    private Integer company_id;
    private Set<Service> services;
    private Set<Product> products;

}
