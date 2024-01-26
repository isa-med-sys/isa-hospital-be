package com.isa.med_hospital.service.impl;

import com.isa.med_hospital.dto.CompanyDto;
import com.isa.med_hospital.repository.CompanyRepository;
import com.isa.med_hospital.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<CompanyDto> findAll() {
        return companyRepository.findAll();
    }
}
