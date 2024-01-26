package com.isa.med_hospital.controller;

import com.isa.med_hospital.dto.CompanyDto;
import com.isa.med_hospital.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> findAllCompanies() {
        List<CompanyDto> result = companyService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findAllCompanies(@PathVariable Long id) {
        CompanyDto result = companyService.findById(id);
        return ResponseEntity.ok(result);
    }
}
