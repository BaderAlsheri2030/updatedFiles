package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class RfpDTO {
    private Integer company_id;
    private Integer competition_id;
    private Integer contract_length;
    private LocalDate dead_line;
    private boolean isComplete;
    private LocalDate time_left;
    @NotNull(message = "competition type cannot be null")
    private String competition_type;
    @NotNull(message = "description cannot be null")
    private String description;
    @NotNull(message = "reference number cannot be null")
    private String reference_number;
    @NotNull(message = "service details cannot be null")
    private String service_details;
    @NotNull(message = "title cannot be null")
    private String title;

}
