package com.isa.med_hospital.service.impl;

import com.isa.med_hospital.dto.ContractDto;
import com.isa.med_hospital.model.Contract;
import com.isa.med_hospital.repository.ContractRepository;
import com.isa.med_hospital.service.ContractService;
import com.isa.med_hospital.util.Mapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final Mapper mapper;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, Mapper mapper) {
        this.contractRepository = contractRepository;
        this.mapper = mapper;
    }


    @Override
    public ContractDto findByUser(Long userId) {
        Contract contract = contractRepository.findByUserIdAndIsActiveTrue(userId);
        return mapper.map(contract, ContractDto.class);
    }

    @Override
    public ContractDto create(ContractDto contractDto) {
        validateContract(contractDto);
        Contract contract = mapper.map(contractDto, Contract.class);
        // TODO deactivate all existing contracts
        contract = contractRepository.save(contract);
        return mapper.map(contract, ContractDto.class);
    }

    @Override
    public ContractDto update(Long contractId, ContractDto contractDto) {
        // TODO
        return null;
    }

    @Override
    public void delete(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found."));
        contract.deactivate();
        contractRepository.save(contract);
    }

    private void validateContract(ContractDto contractDto) {
        if (contractDto.getId() == null || contractDto.getUserId() == null || contractDto.getCompanyId() == null ||
                contractDto.getStartDate() == null || contractDto.getEquipmentQuantities() == null || contractDto.getIsActive() == null) {
            throw new IllegalArgumentException("Some fields of contract are null");
        }
        if (contractDto.getEquipmentQuantities().values().stream().anyMatch(quantity -> quantity <= 0)) {
            throw new IllegalArgumentException("Equipment quantities must be greater than 0");
        }
    }
}
