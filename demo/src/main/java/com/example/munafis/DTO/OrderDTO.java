package com.example.munafis.DTO;


import com.example.munafis.Model.ProductDetails;
import com.example.munafis.Model.Service;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class OrderDTO {
    //edit
    @Pattern(regexp = "^(accepted|pending|completed)$")
    private String status;
    private double totalPrice;
    private Integer product_id;
    private Integer service_id;
    private Set<Service> services;
    private Set<ProductDetails> productsDetails;
    private Integer company_id;


}
