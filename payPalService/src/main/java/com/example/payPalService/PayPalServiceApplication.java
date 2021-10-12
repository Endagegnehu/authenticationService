package com.example.payPalService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@RestController
public class PayPalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayPalServiceApplication.class, args);
    }

    @Value("${activemq.broker.url}")
    private String brokerUrl;

        @Bean
    public Queue queue() {
        return (Queue) new ActiveMQQueue("paid-queue");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(activeMQConnectionFactory());
    }

    @Autowired
    private Queue queue;

    // ObjectMapper instantiation
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "payPal-queue")
    public void consumeMessage(String message) {
        System.out.println(message);
        jmsTemplate.convertAndSend(queue, message);
    }

}
