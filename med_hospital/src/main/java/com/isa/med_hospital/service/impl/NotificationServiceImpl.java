package com.isa.med_hospital.service.impl;

import com.isa.med_hospital.dto.ContractNotificationDto;
import com.isa.med_hospital.model.Notification;
import com.isa.med_hospital.repository.NotificationRepository;
import com.isa.med_hospital.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> findByUser(Long userId) {
        return notificationRepository.findAllByUserId(userId);
    }

    @Override
    public Notification save(ContractNotificationDto notificationDto) {
        Notification notification = new Notification();
        notification.setUserId(notificationDto.getUserId());
        notification.setTimestamp(notificationDto.getTimestamp());
        notification.setMessage(notificationDto.getMessage());

        return notificationRepository.save(notification);
    }
}
