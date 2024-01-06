package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Rfp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(200) not null")
    private String description;
    @Column(columnDefinition = "varchar(50) not null")
    private String referenceNumber;
    @Column(columnDefinition = "varchar(50) not null")
    private String competitionType;
    private LocalDate deadLine;
    @Column(columnDefinition = "int not null")
    private Integer contractLength;
    @Column(columnDefinition = "varchar(50) not null")
    private String serviceDetails;
    @Column(columnDefinition = "varchar(50) not null")
    private String title;
    @Column
    private boolean isComplete;
    private LocalDate timeLeft;

    @ManyToOne
    @JoinColumn(name = "company_id" , referencedColumnName = "id")
    @JsonIgnore
    private Company company;


    @ManyToOne
    @JoinColumn(name = "competition_id" , referencedColumnName = "id")
    @JsonIgnore
    private Competition competition;


}
