package com.jiazhi.study.rabbitmq.oneconsume;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {

    private static final String TASK_QUEUE_NAME="task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("www.cyw.com");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);

        for(int i=0;i<100;i++){
            String messge = "Hello rabbitmq"+ i;
            //属性 MessageProperties.PERSISTENT_TEXT_PLAIN 指的是消息持久化
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,messge.getBytes("UTF-8"));
            System.out.println("NewTask send :"  + messge);
        }
        channel.close();
        connection.close();
    }
}
