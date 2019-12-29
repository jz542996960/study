package com.jiazhi.study.rabbitmq.multconsume;

import com.jiazhi.study.tool.mq.RabbitMqTool;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 一个队列 多个消费者
 */
public class NewTesk {

    public static final String TASK_QUEUE_NAME="multi_consume_task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        RabbitMqTool.buildMqFactoryProperty(factory);

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        //队列名称，是否持久化，是否独占，自动删除 ，参数
        channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);

        //分发消息
        for(int i=0;i<1000;i++){
            String message = "Hello rabbitMQ" + i;
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("new task has send " + message);
        }

        channel.close();
        connection.close();
        while (true){

        }
    }
}
