package com.jiazhi.study.rabbitmq.exchange;

import com.jiazhi.study.tool.mq.RabbitMqTool;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.ConnectException;


/**
 * 发布与订阅模式
 */
public class EmitLog {

    private static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        RabbitMqTool.buildMqFactoryProperty(factory);

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        for(int i=0; i<5;i++){
            String msg = "send log" + i;
            channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
            System.out.println("emitlog send" + msg);
        }

        channel.close();
        connection.close();
    }
}
