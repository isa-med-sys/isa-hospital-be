package com.isa.med_hospital.rabbitMQ;

import com.isa.med_hospital.dto.ContractDto;
import com.isa.med_hospital.dto.ContractGetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQEquipmentProducer {

    @Value("${rabbitmq.equip-producer.handle.exchange.name}")
    private String handleExchange;

    @Value("${rabbitmq.equip-producer.handle.routing.key.name}")
    private String handleRoutingKey;

    @Value("${rabbitmq.equip-producer.get.exchange.name}")
    private String getExchange;

    @Value("${rabbitmq.equip-producer.get.routing.key.name}")
    private String getRoutingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQEquipmentProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEquipmentProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void getContract(ContractGetDto message) {
        LOGGER.info(String.format("Contract to get -> %s", message));
        rabbitTemplate.convertAndSend(getExchange, getRoutingKey, message);
    }

    public void sendContract(ContractDto message) {
        LOGGER.info(String.format("Contract sent -> %s", message));
        rabbitTemplate.convertAndSend(handleExchange, handleRoutingKey, message);
    }
}
