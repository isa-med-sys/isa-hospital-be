package com.isa.med_hospital.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.equip-producer.handle.queue.name}")
    private String queue1;
    @Value("${rabbitmq.equip-producer.handle.exchange.name}")
    private String exchange1;
    @Value("${rabbitmq.equip-producer.handle.routing.key.name}")
    private String routingKey1;

    @Value("${rabbitmq.equip-producer.get.queue.name}")
    private String queue2;
    @Value("${rabbitmq.equip-producer.get.exchange.name}")
    private String exchange2;
    @Value("${rabbitmq.equip-producer.get.routing.key.name}")
    private String routingKey2;


    @Bean
    public Queue queue1(){
        return new Queue(queue1);
    }

    @Bean
    public TopicExchange exchange1(){
        return new TopicExchange(exchange1);
    }

    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(queue1()).to(exchange1()).with(routingKey1);
    }

    @Bean
    public Queue queue2(){
        return new Queue(queue2);
    }

    @Bean
    public TopicExchange exchange2(){
        return new TopicExchange(exchange2);
    }

    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(queue2()).to(exchange2()).with(routingKey2);
    }

    @Bean
    public MessageConverter converter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, MessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
}