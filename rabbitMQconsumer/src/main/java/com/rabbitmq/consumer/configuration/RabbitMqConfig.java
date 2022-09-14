package com.rabbitmq.consumer.configuration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    public static final String QUEUE1="success_msg_queue";

    public static final String QUEUE2="failed_msg_queue";
    public static final String EXCHANGE="message_exchange";

    public static final String ROUTINGKEY1="success_msg_routingKey";

    public static final String ROUTINGKEY2="failed_msg_routingKey";


    @Bean
    public Queue queue1() {
        return new Queue(QUEUE1);
    }

    @Bean
    public Queue queue2() {
        return new Queue(QUEUE2);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    //now we'll bind exchange and queue using key
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(exchange()).with(ROUTINGKEY1);
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue2()).to(exchange()).with(ROUTINGKEY2);
    }

    //message converter...data passing as a class which should be converted to json which will be done through message converter
    @Bean
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return  template;
    }

}
