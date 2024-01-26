package com.isa.med_hospital.repository;

import com.isa.med_hospital.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    Contract findByUserIdAndIsActiveTrue(Long userId);
    List<Contract> findByUserId(Long userId);
}
