package com.rabbitmq.producer.acknowledgement;

import com.rabbitmq.producer.configuration.RabbitMqConfig;
import com.rabbitmq.producer.entity.CustomMessage;
import com.rabbitmq.producer.responseModal.Response;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerAckPublisher {

    @Autowired
    private RabbitTemplate template;

    //fetching messages from queue2
//    @RabbitListener(queues = RabbitMqConfig.QUEUE2)
//    public void listener(Response responseFromConsumer) {
//        JSONObject responseJson=new JSONObject(responseFromConsumer);
//        System.out.println("Response from consumer "+responseJson);
//    }


//    @RabbitListener(queues = RabbitMqConfig.QUEUE2)
//    public void listener(CustomMessage customMessage) {
//        JSONObject failedJson=new JSONObject(customMessage);
//        System.out.println(failedJson);
//    }

}
