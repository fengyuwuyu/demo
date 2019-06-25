package com.ll.demo.rabbitmq.consumer;

import java.io.IOException;

import com.ll.demo.rabbitmq.producer.ProducerDemo;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ConsumerDemo {

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

	channel.exchangeDeclare(ProducerDemo.EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, null);
    channel.queueDeclare(ProducerDemo.QUEUE_NAME, false, false, false, null);
    channel.queueBind(ProducerDemo.QUEUE_NAME, ProducerDemo.EXCHANGE_NAME, "hello");
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    Consumer consumer = new DefaultConsumer(channel) { 
    	@Override 
    	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException { 
    		String msg = new String(body, "UTF-8"); 
    		System.out.println("Received message : '" + msg + "'"); 
		} 
	};// 开始获取消息
    channel.basicConsume(ProducerDemo.QUEUE_NAME, true, consumer);
  }
}