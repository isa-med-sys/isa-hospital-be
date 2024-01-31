package com.isa.med_hospital.repository.impl;

import com.isa.med_hospital.dto.CompanyDto;
import com.isa.med_hospital.dto.EquipmentDto;
import com.isa.med_hospital.repository.CompanyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public CompanyDto findById(Long id) {
        String sql = "SELECT c.id AS id, c.name AS name, e.id AS equipment_id, e.name AS equipment_name " +
                "FROM companies c " +
                "LEFT JOIN company_equipment ce ON c.id = ce.company_id " +
                "LEFT JOIN equipment e ON ce.equipment_id = e.id " +
                "WHERE c.id = :companyId";

        List<Object[]> resultList = entityManager.createNativeQuery(sql)
                .setParameter("companyId", id)
                .getResultList();

        CompanyDto companyDto = null;

        for (Object[] row : resultList) {
            if (companyDto == null) {
                Long companyId = (Long) row[0];
                String companyName = (String) row[1];

                companyDto = new CompanyDto();
                companyDto.setId(companyId);
                companyDto.setName(companyName);
                companyDto.setEquipment(new ArrayList<>());
            }

            Long equipmentId = (Long) row[2];
            String equipmentName = (String) row[3];

            if (equipmentId != null) {
                EquipmentDto equipmentDto = new EquipmentDto();
                equipmentDto.setId(equipmentId);
                equipmentDto.setName(equipmentName);
                companyDto.getEquipment().add(equipmentDto);
            }
        }

        return companyDto;
    }

    public List<CompanyDto> findAll() {
        String sql = "SELECT c.id AS id, c.name AS name, e.id AS equipment_id, e.name AS equipment_name " +
                "FROM companies c " +
                "LEFT JOIN company_equipment ce ON c.id = ce.company_id " +
                "LEFT JOIN equipment e ON ce.equipment_id = e.id";

        List<Object[]> resultList = entityManager.createNativeQuery(sql).getResultList();

        Map<Long, CompanyDto> companyDtoMap = new HashMap<>();

        for (Object[] row : resultList) {
            Long companyId = (Long) row[0];
            String companyName = (String) row[1];
            Long equipmentId = (Long) row[2];
            String equipmentName = (String) row[3];

            CompanyDto companyDto = companyDtoMap.computeIfAbsent(companyId, id -> {
                CompanyDto dto = new CompanyDto();
                dto.setId(companyId);
                dto.setName(companyName);
                dto.setEquipment(new ArrayList<>());
                return dto;
            });

            if (equipmentId != null) {
                EquipmentDto equipmentDto = new EquipmentDto();
                equipmentDto.setId(equipmentId);
                equipmentDto.setName(equipmentName);
                companyDto.getEquipment().add(equipmentDto);
            }
        }

        return new ArrayList<>(companyDtoMap.values());
    }
}
