package com.isa.med_hospital.controller;

import com.isa.med_hospital.dto.ContractDto;
import com.isa.med_hospital.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public ResponseEntity<List<ContractDto>> findAllContractsByUser(@RequestParam Long userId) {
        List<ContractDto> result = contractService.findAllByUser(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/active")
    public ResponseEntity<ContractDto> findActiveContractByUser(@RequestParam Long userId) {
        ContractDto result = contractService.findActiveByUser(userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ContractDto> createContract(@RequestBody ContractDto contractDto) {
        ContractDto result = contractService.create(contractDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractDto> updateContract(@PathVariable Long id, @RequestBody ContractDto contractDto) {
        ContractDto result = contractService.update(id, contractDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
