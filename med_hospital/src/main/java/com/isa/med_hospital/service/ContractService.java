package com.isa.med_hospital.service;

import com.isa.med_hospital.dto.ContractDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContractService {

    List<ContractDto> findAllByUser(Long userId);
    ContractDto findActiveByUser(Long userId);
    ContractDto create(ContractDto contractDto);
    ContractDto update(Long contractId, ContractDto contractDto);
    void delete(Long contractId);
}
