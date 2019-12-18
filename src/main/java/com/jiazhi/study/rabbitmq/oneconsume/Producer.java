package com.jiazhi.study.rabbitmq.oneconsume;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    private static final String QUEUE_NAME = "Rabbitq_MQ_TEST";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory  connectionFactory  = new ConnectionFactory();
        connectionFactory.setHost("www.cyw.com");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setPort(5672);
        //创建一个连接
        Connection connection = connectionFactory.newConnection();
        //创建一个通道
        Channel  channel = connection.createChannel();
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        for(int i=0;i<1000;i++){
            String message = "hello world";
            message += i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));
        }
        System.out.println("Producer send" );
        channel.close();
        connection.close();


    }
}
