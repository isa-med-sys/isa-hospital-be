package com.isa.med_hospital.service;

import com.isa.med_hospital.dto.CompanyDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {

    CompanyDto findById(Long id);
    List<CompanyDto> findAll();
}
