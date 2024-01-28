package com.isa.med_hospital.service;

import com.isa.med_hospital.dto.ContractDto;
import org.springframework.stereotype.Service;

@Service
public interface ContractService {

    void findActiveByUser(Long userId);
    void create(ContractDto contractDto);
    void update(Long contractId, ContractDto contractDto);
    void delete(Long contractId);
}
