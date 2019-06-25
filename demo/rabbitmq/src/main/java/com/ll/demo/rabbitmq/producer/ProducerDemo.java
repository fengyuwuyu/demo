package com.ll.demo.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerDemo {
	public final static String QUEUE_NAME = "ORIGIN_QUEUE"; 
	public final static String EXCHANGE_NAME = "hello-exchange"; 
	public static void main(String[] args) throws Exception { 
		ConnectionFactory factory = new ConnectionFactory(); // 连接IP 
//		factory.setVirtualHost(""); //设置虚拟主机
		factory.setHost("127.0.0.1"); // 连接端口 
		factory.setPort(5672); // 虚拟机 
		factory.setVirtualHost("/"); // 用户 
		factory.setUsername("guest"); 
		factory.setPassword("guest"); // 建立连接 
		String msg = "Hello world, Rabbit MQ"; 
		try (Connection conn = factory.newConnection(); Channel channel = conn.createChannel();) {
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, null);
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);// 声明队列  
			channel.basicPublish(EXCHANGE_NAME, QUEUE_NAME, null, msg.getBytes()); 
			System.out.println(" producer sent '" + msg + "'");
		} 
		
	}
}
