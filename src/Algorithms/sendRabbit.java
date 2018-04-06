package Algorithms;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class sendRabbit {
	private final static String QUEUE_NAME = "vehicle";
	
	public static void _sendData_() throws Exception {
	
		//RabbitMQ Server Credentials
    	ConnectionFactory factory = new ConnectionFactory();
    	factory.setAutomaticRecoveryEnabled(false);
    	factory.setUsername("amudalab3");
    	factory.setPassword("amudalab");
    	factory.setHost("172.17.137.160");
    	factory.setPort(5672);
    	factory.setVirtualHost("amudavhost");
    	Connection connection = factory.newConnection();
    	Channel channel = connection.createChannel();
	    	
    	//Retrieving values from HaspMap and send to RabbitMQ Server
		Set set = TagReportListenerImplementation.hm.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
             Map.Entry mentry = (Map.Entry)iterator.next();
             String data = mentry.getValue().toString();
             channel.queueDeclare(QUEUE_NAME, true, false, false, null);
             channel.basicPublish("", QUEUE_NAME, null, data.getBytes("UTF-8"));
             System.out.println(" [x] Sent TAG REPORT '" + mentry.getValue() + "'");
        }
        //Clearing HaspMap entries
        TagReportListenerImplementation.hm.clear(); 
        
	}

}


