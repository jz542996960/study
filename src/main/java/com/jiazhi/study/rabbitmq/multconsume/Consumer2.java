package com.jiazhi.study.rabbitmq.multconsume;

import com.jiazhi.study.tool.mq.RabbitMqTool;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer2 {

    public static final String TASK_QUEUE_NAME="multi_consume_task_queue";

    public static void main(String[] args) throws  Exception {

        ConnectionFactory factory = new ConnectionFactory();

        RabbitMqTool.buildMqFactoryProperty(factory);

        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME,true,false,false, null );
        //每次从队列拿取1个任务
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                channel.basicAck(envelope.getDeliveryTag(),false);
                try{
                    String messgage = new String(body,"UTF-8");
                    System.out.println("worked2 recevice " +  messgage );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME,autoAck,consumer);

        while (true){

        }
    }
}
