package com.isa.med_hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto {

    private Long id;
    private Long userId;
    private Long companyId;
    private LocalDate startDate;
    private Map<Long, Integer> equipmentQuantities; // map equipmentId - quantity
    private Boolean isActive;
}
