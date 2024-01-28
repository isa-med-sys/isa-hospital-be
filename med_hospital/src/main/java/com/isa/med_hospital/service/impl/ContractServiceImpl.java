package com.isa.med_hospital.service.impl;

import com.isa.med_hospital.dto.ContractDto;
import com.isa.med_hospital.dto.ContractGetDto;
import com.isa.med_hospital.rabbitMQ.RabbitMQEquipmentProducer;
import com.isa.med_hospital.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    private final RabbitMQEquipmentProducer mqEquipmentProducer;

    @Autowired
    public ContractServiceImpl(RabbitMQEquipmentProducer mqEquipmentProducer) {
        this.mqEquipmentProducer = mqEquipmentProducer;
    }

    @Override
    public void findActiveByUser(Long userId) {
        ContractGetDto contractDto = new ContractGetDto();
        contractDto.setUserId(userId);
        mqEquipmentProducer.getContract(contractDto);
    }

    @Override
    public void create(ContractDto contractDto) {
        validateContract(contractDto);
        mqEquipmentProducer.sendContract(contractDto);
    }

    @Override
    public void update(Long contractId, ContractDto contractDto) {
        validateContract(contractDto);
        contractDto.setId(contractId);
        mqEquipmentProducer.sendContract(contractDto);
    }

    @Override
    public void delete(Long contractId) {
        ContractDto contractDto = new ContractDto();
        contractDto.setId(contractId);
        contractDto.setDelete(true);
        mqEquipmentProducer.sendContract(contractDto);
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
}
