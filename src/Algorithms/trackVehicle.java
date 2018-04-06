package Algorithms;

public class trackVehicle {

	public static void main(String[] args) throws Exception {
	
		while(true)		{
			//Start the Reader
			startUp._startReader_(args);
			//Send observed tag data to RabbitMQ Server
			sendRabbit._sendData_();
			
		}
		
	
	}

}
 