package com.rabbitmq.consumer.listener;

import com.rabbitmq.consumer.configuration.RabbitMqConfig;
import com.rabbitmq.consumer.entity.CustomMessage;
import com.rabbitmq.consumer.responseModal.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MessageListener {

    public static final Logger log = LogManager.getLogger(MessageListener.class);

    @Autowired
    private RabbitTemplate template;

    //consumer listening(consuming) message
    @RabbitListener(queues = RabbitMqConfig.QUEUE1)
    public void listener(CustomMessage customMessage) {
        try {

            log.info("Message received by consumer {}",customMessage);

            Response response = callingThirdPartyApi(customMessage);
            System.out.println(response);
            log.info("Response from third party api {}", response);

            if(response==null) {
                System.out.println("External api down");                    //null response
                failedMsgToQueue(customMessage);                            //saving msg to second queue
//                failedMsgListener(customMessage);
            }
            else if (response.getResponseCode().equals("200")) {
                //save in db
                log.info("Success response from external api {}", response);
            } else if (response.getResponseCode().equals("401")) {
                log.info("Error response from api {}",response);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }


    public Response callingThirdPartyApi(CustomMessage customMessage) {

        try {
            log.info("Calling third party api with message {}",customMessage);
            String uri = "http://localhost:8080/externalUrl";
            RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.getForObject(uri, CustomMessage.class);
            return restTemplate.postForObject(uri, customMessage, Response.class);

        }
        catch (Exception e) {
            log.info("Exception occur while listening from failed_msg_queue {}",e.getMessage());
        }

        return null;
    }


    public void failedMsgToQueue(CustomMessage customMessage) {
        try {
            System.out.println("Saving msg to second queue");
            template.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.ROUTINGKEY2, customMessage);
        }
        catch (NullPointerException e) {
            log.info("NullPointerException occur while listening from failed_msg_queue {}",e.getMessage(),e);
        }
        catch (Exception e) {
            log.info("NullPointerException occur while listening from failed_msg_queue {}",e.getMessage());
        }

    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE2)
    public void failedMsgListener(CustomMessage customMessage) {

        try {
            log.info("Message received from failed_msg_queue {}",customMessage);
        }
        catch (NullPointerException e) {
            log.info("NullPointerException occur while listening from failed_msg_queue {}",e.getMessage(),e);
        }
        catch (Exception e) {
            log.info("NullPointerException occur while listening from failed_msg_queue {}",e.getMessage());
        }

    }


}
