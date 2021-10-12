package com.example.orderservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.jms.Queue;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableJms
public class PaymentServiceApplication {

    final Logger log = LoggerFactory.getLogger(PaymentServiceApplication.class);

    @Value("${activemq.broker.url}")
    private String brokerUrl;

//    @Bean
//    public Queue queue() {
//        return (Queue) new ActiveMQQueue("orderplaced-queue");
//    }
//
//    @Bean
//    public Queue paymentQueue() {
//        return (Queue) new ActiveMQQueue("orderpay-queue");
//    }

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
    PaymentRepository paymentRepository;

    // ObjectMapper instantiation
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JmsTemplate jmsTemplate;

//    @Autowired
//    private Queue queue = (Queue) new ActiveMQQueue("orderplaced-queue");
    private Queue bankQueue = (Queue) new ActiveMQQueue("bank-queue");
    private Queue payPalQueue = (Queue) new ActiveMQQueue("payPal-queue");

    @JmsListener(destination = "orderplaced-queue")
    public void consumeMessage(String message){
        System.out.println(message);
        Payment payment = null;
        try {
            payment = objectMapper.readValue(message, Payment.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        payment.setStatus("process");

        paymentRepository.save(payment);

        ObjectMapper mapper = new ObjectMapper();

        String json = null;

        try {
            json = mapper.writeValueAsString(payment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if(payment.getPreferredPayment().equals("bankService")) {
            jmsTemplate.convertAndSend(bankQueue, json);
        } else if(payment.getPreferredPayment().equals("payPalService")) {
            jmsTemplate.convertAndSend(payPalQueue, json);
            System.out.println(json);
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }


}
