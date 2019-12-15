package com.jiazhi.study.multconsume;

import com.jiazhi.study.tool.mq.RabbitMqTool;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消费者1 手动ack，中间抛出异常 ，
 */
public class Consumer1 {

    public static final String TASK_QUEUE_NAME="multi_consume_task_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        RabbitMqTool.buildMqFactoryProperty(factory);
        Connection  connection = factory.newConnection();
        final Channel  channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME,true,false,false, null );
        //每次从队列拿取1个任务
        channel.basicQos(1);

        Consumer  consumer = new DefaultConsumer(channel){
            @Override
            public void handleConsumeOk(String consumerTag) {
                System.out.println("consumeTag = " + consumerTag);
            }

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String messgage = new String(body,"UTF-8");
                System.out.println("worked 1 recevice " +  messgage );
                try{
                    throw new Exception();
                }catch (Exception e){
                    channel.abort();
                }finally {
                    System.out.println("wored 1 done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME,autoAck,consumer);
    }
}
