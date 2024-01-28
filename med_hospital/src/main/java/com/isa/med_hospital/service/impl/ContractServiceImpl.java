package com.isa.med_hospital.service.impl;

import com.isa.med_hospital.dto.ContractDto;
import com.isa.med_hospital.model.Contract;
import com.isa.med_hospital.repository.ContractRepository;
import com.isa.med_hospital.service.ContractService;
import com.isa.med_hospital.util.Mapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final Mapper mapper;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, Mapper mapper) {
        this.contractRepository = contractRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ContractDto> findAllByUser(Long userId) {
        List<Contract> contract = contractRepository.findByUserId(userId);
        return contract == null
                ? null
                : mapper.mapList(contract, ContractDto.class);
    }

    @Override
    public ContractDto findActiveByUser(Long userId) {
        Contract contract = contractRepository.findByUserIdAndIsActiveTrue(userId);
        return contract == null
                ? null
                : mapper.map(contract, ContractDto.class);
    }

    @Override
    public ContractDto create(ContractDto contractDto) {
        validateContract(contractDto);

        Contract contract = createContract(contractDto);

        Long userId = contractDto.getUserId();
        deactivateExistingContract(userId);

        contract = contractRepository.save(contract);
        return mapper.map(contract, ContractDto.class);
    }

    @Override
    public ContractDto update(Long contractId, ContractDto contractDto) {
        validateContract(contractDto);

        Contract existingContract = contractRepository.findById(contractId)
                    .orElseThrow(() -> new EntityNotFoundException("Contract not found."));

        if(!existingContract.getIsActive()) {
            throw new IllegalStateException("Contract not active.");
        }

        if(!Objects.equals(existingContract.getCompanyId(), contractDto.getCompanyId())) {
            return create(contractDto);
        }
        existingContract.setStartDate(contractDto.getStartDate());
        existingContract.setEquipmentQuantities(contractDto.getEquipmentQuantities());

        existingContract = contractRepository.save(existingContract);
        return mapper.map(existingContract, ContractDto.class);
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
            throw new IllegalArgumentException("Some fields of the contract are null");
        }

        if (contractDto.getEquipmentQuantities().values().stream().anyMatch(quantity -> quantity <= 0)) {
            throw new IllegalArgumentException("Equipment quantities must be greater than 0");
        }

        if (contractDto.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start Date can't be in the past");
        }
    }

    private Contract createContract(ContractDto contractDto) {
        Contract contract = new Contract();
        contract.setUserId(contractDto.getUserId());
        contract.setCompanyId(contractDto.getCompanyId());
        contract.setStartDate(contractDto.getStartDate());
        contract.setEquipmentQuantities(contractDto.getEquipmentQuantities());
        contract.setIsActive(true);
        return contract;
    }

    private void deactivateExistingContract(Long userId) {
        Contract existingContract = contractRepository.findByUserIdAndIsActiveTrue(userId);
        if(existingContract == null) return;

        existingContract.deactivate();
        contractRepository.save(existingContract);
    }
}
