package com.isa.med_hospital.rabbitMQ;

import com.isa.med_hospital.dto.ContractNotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQEquipmentConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQEquipmentConsumer.class);
    private final SimpMessagingTemplate simpMessagingTemplate;

    public RabbitMQEquipmentConsumer(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = {"${rabbitmq.equip-consumer.queue.name}"})
    public void upcomingContracts(ContractNotificationDto notification){
        LOGGER.info(String.format("Received message -> %s", notification.toString()));

        // TODO save notifications

        String userDestination = "/socket-publisher/" + notification.getUserId();
        this.simpMessagingTemplate.convertAndSend(userDestination, notification);
    }
}