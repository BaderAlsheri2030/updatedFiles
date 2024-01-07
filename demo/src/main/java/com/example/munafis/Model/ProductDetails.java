package com.example.munafis.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "int not null")
    private Integer quantity;


    @ManyToOne
    @JoinColumn(name = "product_id" , referencedColumnName = "id")
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id" , referencedColumnName = "id")
    @JsonIgnore
    private Orderr order;

}