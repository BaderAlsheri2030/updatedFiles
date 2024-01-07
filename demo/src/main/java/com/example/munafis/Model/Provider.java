package com.example.munafis.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(50) not null unique")
    @NotNull(message = "UserName cannot be null")
    private String username;
    @NotNull(message = "Password cannot be null")
    private String password;
    @Column(columnDefinition = "varchar(50) not null unique")
    @Email(message = "Must be a valid email format")
    @NotNull(message = "email cannot be null")
    private String email;
    @Column(columnDefinition = "varchar(50) not null")
    @NotNull(message = "company name cannot be null")
    private String companyName;
    @Column(columnDefinition = "varchar(50) not null unique")
    @NotNull(message = "business number cannot be null")
    private String businessNumber;
    @Column(columnDefinition = "varchar(50) not null")
    @NotNull(message = "address cannot be null")
    private String address;

    //@Column(columnDefinition = "varchar(8) check(role = 'Company' or role='Provider')")
    //@NotNull(message = "role cannot be null")
    private String role;
    @Column(columnDefinition = "varchar(50) not null")
    @NotNull(message = "field cannot be null")
    private String field;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "provider")
    private Set<Service> services;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "provider")
    private Set<Product> products;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "provider")
    private Set<Offer> offers;
}
