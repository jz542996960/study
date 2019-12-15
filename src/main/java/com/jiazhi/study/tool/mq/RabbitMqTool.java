package com.jiazhi.study.tool.mq;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitMqTool {

    public static void buildMqFactoryProperty(ConnectionFactory connectionFactory){

        connectionFactory.setPort(5672);
        connectionFactory.setPassword("admin");
        connectionFactory.setUsername("admin");
        connectionFactory.setHost("www.cyw.com");
    }
}
