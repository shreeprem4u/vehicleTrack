package Algorithms;


import com.impinj.octane.ImpinjReader;
import com.impinj.octane.Tag;
import com.impinj.octane.TagReport;
import com.impinj.octane.TagReportListener;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class TagReportListenerImplementation implements TagReportListener {

	public static String epc;
	public static int port;
	public static String time;
	
	public static HashMap<String, String> hm = new HashMap<String, String>();

    @Override
    public void onTagReported(ImpinjReader reader, TagReport report) {
 
        List<Tag> tags = report.getTags();
     
        for (Tag t : tags) {

			Date now = new Date();
			SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			  
			String epc= t.getEpc().toString();
			int port = t.getAntennaPortNumber();
			String time = timeformat.format(now);
			String date = dateformat.format(now);
			          
			  
			String mes = epc+","+port+","+time+","+date;
			String key = epc; 					
			
			hm.putIfAbsent(key, mes);
     
        }
        
        try { 
	          Set set = hm.entrySet();
	          Iterator iterator = set.iterator();
	          while(iterator.hasNext()) {
	             Map.Entry mentry = (Map.Entry)iterator.next();
	//           System.out.println("In Listener:"+mentry.getValue());
	          }
    
      	}  catch(Exception e){System.out.println("Exception in HashMap Iteration");}
    }
}
