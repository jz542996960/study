package com.jiazhi.study.oneconsume;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    private static final String QUEUE_NAME="Rabbitq_MQ_TEST";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("www.cyw.com");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setPort(5672);

        Connection connection = connectionFactory.newConnection();
        Channel  channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        System.out.println("customer wait to recv msg");

        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println(Thread.currentThread().getName());
                System.out.println("customer reveived : "  + message);
             }
        };

        channel.basicConsume(QUEUE_NAME,true,consumer);

        while(true){

        }
    }
}
