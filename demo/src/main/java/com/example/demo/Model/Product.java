package com.example.demo.Model;

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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(225) not null")
    private String name;
    @Column(columnDefinition = "double not null")
    private double price;
    @Column(columnDefinition = "int not null")
    private Integer stock;



    @ManyToOne
    @JoinColumn(name = "provider_id" , referencedColumnName = "id")
    @JsonIgnore
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "order_id" , referencedColumnName = "id")
    @JsonIgnore
    private OrderModel order;



    @OneToOne(cascade = CascadeType.ALL,mappedBy = "product")
    @PrimaryKeyJoinColumn
    private ProductDetails productsDetails;

}
