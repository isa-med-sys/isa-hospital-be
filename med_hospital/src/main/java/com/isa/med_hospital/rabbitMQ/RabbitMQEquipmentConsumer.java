package com.isa.med_hospital.rabbitMQ;

import com.isa.med_hospital.dto.ContractDto;
import com.isa.med_hospital.dto.ContractNotificationDto;
import com.isa.med_hospital.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQEquipmentConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQEquipmentConsumer.class);
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;

    public RabbitMQEquipmentConsumer(SimpMessagingTemplate simpMessagingTemplate, NotificationService notificationService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = {"${rabbitmq.equip-consumer.contract.queue.name}"})
    public void getContract(ContractDto message){
        LOGGER.info(String.format("Received message -> %s", message.toString()));
        String userDestination = "/socket-publisher/contracts/" + message.getUserId();
        this.simpMessagingTemplate.convertAndSend(userDestination, message);
    }

    @RabbitListener(queues = {"${rabbitmq.equip-consumer.notif.queue.name}"})
    public void notifyAboutUpcomingContracts(ContractNotificationDto message){
        LOGGER.info(String.format("Received message -> %s", message.toString()));
        notificationService.save(message);
        String userDestination = "/socket-publisher/notifications/" + message.getUserId();
        this.simpMessagingTemplate.convertAndSend(userDestination, message);
    }
}