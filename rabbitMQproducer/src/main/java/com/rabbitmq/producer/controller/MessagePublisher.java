package com.rabbitmq.producer.controller;

import com.rabbitmq.producer.configuration.RabbitMqConfig;
import com.rabbitmq.producer.entity.CustomMessage;
import com.rabbitmq.producer.responseModal.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class MessagePublisher {

    public static final Logger log = LogManager.getLogger(MessagePublisher.class);

    @Autowired
    private RabbitTemplate template;


    @PostMapping("/publish")
    public Response publishMessage(@RequestBody CustomMessage customMessage) {
        Response response=new Response();
        try {
            customMessage.setMessageId(UUID.randomUUID().toString());
            customMessage.setMessageDate(String.valueOf(new Date()));
            JSONObject jsonObject=new JSONObject(customMessage);
            log.info("Sending message to consumer {}",jsonObject);
            template.convertAndSend(RabbitMqConfig.EXCHANGE,RabbitMqConfig.ROUTINGKEY1,customMessage);

            response.setResponseCode("200");
            response.setResponseMessage("Message sent");
            response.setResponseData(jsonObject.toMap());
            return response;

        } catch (Exception e) {

        }

        return response;
    }



}

