package com.isa.med_hospital.repository;

import com.isa.med_hospital.dto.CompanyDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository {

    List<CompanyDto> findAll();
}
