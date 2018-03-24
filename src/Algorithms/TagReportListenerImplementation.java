package Algorithms;

//import com.rabbitmq.client.Channel;

//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;

//import com.example.sdksamples.*;
import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;
import com.impinj.octane.TxPowerTableEntry;
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class TagReportListenerImplementation implements TagReportListener {
//	private final static String QUEUE_NAME = "a";
//	Channel channel;
//	private static String[] args;
	public static String epc;
	public static int port;
	public static String time;
	
	static HashMap<String, String> hm = new HashMap<String, String>();

    @Override
    public void onTagReported(ImpinjReader reader, TagReport report) {
    	if(!hm.isEmpty())
         {
         	hm.clear();
         }
       
	       	
        List<Tag> tags = report.getTags();
       
/*        try{
        	ConnectionFactory factory = new ConnectionFactory();
        
      //Start for connecting RAbbitMq Server		
      		factory.setAutomaticRecoveryEnabled(false);
      	    factory.setUsername("test");
      	    factory.setPassword("test");
      	    factory.setHost("172.17.137.155");
      	    factory.setPort(5672);
      	    factory.setVirtualHost("amudavhost");
      	   
      	//Start for connecting RAbbitMq Server	
  //     factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        }
        catch(Exception e){System.out.println("");}
  */           
        
       
       
        for (Tag t : tags) {

			Date now = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    ft.format(now);
			  
			String epc= t.getEpc().toString();
			int port = t.getAntennaPortNumber();
			String time = ft.format(now);
			          
			  
			String mes = epc+","+port+","+time;
			String key = epc; 					
			
			hm.putIfAbsent(key, mes);
     
        }
        try { 
	          Set set = hm.entrySet();
	          Iterator iterator = set.iterator();
	          while(iterator.hasNext()) {
	             Map.Entry mentry = (Map.Entry)iterator.next();
	//             String data = mentry.getValue().toString();
	 //            channel.basicPublish("", QUEUE_NAME, null, data.getBytes("UTF-8"));
	//             System.out.println(" [x] Sent TAG REPORT '" + data + "'");
	             System.out.println(mentry.getValue());
	          }
    
      	}  catch(Exception e){System.out.println("NO RABBITMQ");}
    }
    
  
}
