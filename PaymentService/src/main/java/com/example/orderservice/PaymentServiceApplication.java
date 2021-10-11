package com.example.orderservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.tomcat.util.json.JSONParser;
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
import javax.xml.crypto.Data;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableJms
public class PaymentServiceApplication {

    @Value("${activemq.broker.url}")
    private String brokerUrl;

    @Bean
    public Queue queue() {
        return (Queue) new ActiveMQQueue("orderplaced-queue");
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
    PaymentRepository paymentRepository;

    // ObjectMapper instantiation
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    BankFeignClient bankFeignClient;

    @Autowired
    PaypalFeignClient paypalFeignClient;

    @JmsListener(destination = "orderplaced-queue")
    public void consumeMessage(String message){
        System.out.println(message);
        Payment payment = null;
        try {
            payment = objectMapper.readValue(message, Payment.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(payment);

        if(payment.getPreferredPayment() == "bankService"){
            bankFeignClient.pay(payment);
        } else if(payment.getPreferredPayment() == "PaypalService"){
            paypalFeignClient.pay(payment);
        }

        payment.setStatus("done");
        paymentRepository.save(payment);
    }

    @FeignClient(name = "bankService")
    interface BankFeignClient {
        @PostMapping("")
        Payment pay(@RequestBody() Payment payment);
    }

    @FeignClient(name = "paypalService")
    interface PaypalFeignClient {
        @PostMapping("")
        Payment pay(@RequestBody() Payment payment);
    }

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }


}
