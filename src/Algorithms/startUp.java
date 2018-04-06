package Algorithms;

import com.impinj.octane.*;

public class startUp {

	static ImpinjReader reader = new ImpinjReader();
	
	public static void _startReader_(String[] args) {
		try {
			 if (args.length < 1) {
		            System.out.print("Must pass at least one reader hostname or IP as argument 1");
		            return;
		        }
         
			 // Connect
	         System.out.println("Connecting to Reader_" + args[0]);
	         reader.connect(args[0]);
	
	         // Get the default settings
	         Settings settings = reader.queryDefaultSettings();
	
	         // send a tag report for every tag read
	         settings.getReport().setMode(ReportMode.Individual);
	         settings.getReport().setIncludePeakRssi(false);
	         settings.getReport().setIncludeAntennaPortNumber(true);
	
	         // setting antennas
	         AntennaConfigGroup ac = settings.getAntennas();
	
	         ac.disableAll();
	         ac.getAntenna((short) 1).setEnabled(true);
	         ac.getAntenna((short) 2).setEnabled(true);
	         
	         ac.getAntenna((short) 1).setIsMaxTxPower(false);
	         ac.getAntenna((short) 2).setIsMaxTxPower(false);
	         
	         ac.getAntenna((short) 1).setTxPowerinDbm(20.0);
	         ac.getAntenna((short) 2).setTxPowerinDbm(20.0);
	         
	         // Apply the new settings
	         reader.applySettings(settings);
	         
	         // connect a listener
	         reader.setTagReportListener(new TagReportListenerImplementation());
	         
	         // Start the reader
	         reader.start();
         try {
	 			Thread.sleep(500);
	 		} catch (InterruptedException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
         	// Stop the reader
	        reader.stop();
	        // Disconnect the reader
	        reader.disconnect();
	    	    
		}catch (OctaneSdkException ex) {
            System.out.println("Exception in Reader Operation"+ ex.toString());
        }
			
	}
}
	        



