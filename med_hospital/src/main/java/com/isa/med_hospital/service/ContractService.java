package com.isa.med_hospital.service;

import com.isa.med_hospital.dto.ContractDto;
import org.springframework.stereotype.Service;

@Service
public interface ContractService {

    ContractDto findByUser(Long userId);
    ContractDto create(ContractDto contractDto);
    ContractDto update(Long contractId, ContractDto contractDto);
    void delete(Long contractId);
}
