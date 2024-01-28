package com.isa.med_hospital.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractNotificationDto {

    private Long userId;
    private LocalDateTime timestamp;
    private String message;
}
