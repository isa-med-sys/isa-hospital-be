package com.isa.med_hospital.controller;

import com.isa.med_hospital.dto.ContractDto;
import com.isa.med_hospital.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/active")
    public ResponseEntity<Void> findActiveContractByUser(@RequestParam Long userId) {
        contractService.findActiveByUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> createContract(@RequestBody ContractDto contractDto) {
        contractService.create(contractDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateContract(@PathVariable Long id, @RequestBody ContractDto contractDto) {
        contractService.update(id, contractDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.delete(id);
        return ResponseEntity.noContent().build();
    }
}