package com.isa.med_hospital.service;

import com.isa.med_hospital.dto.ContractNotificationDto;
import com.isa.med_hospital.model.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    List<Notification> findByUser(Long userId);
    Notification save(ContractNotificationDto notification);
}
