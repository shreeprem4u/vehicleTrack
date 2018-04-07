import psycopg2
import pika
import datetime

dict={
"0000 0000 0000 0000 3236 3331"	: "Veh_3236", "0000 0000 0000 0000 3435 3331" : "Veh_3435"
}

def dbInsert(vehID,epc,time,status):
	try:
		conn = psycopg2.connect("dbname='vehicleTracking' port='5432' user='amudalab3' host='172.17.137.160' password='amudalab'")
		cur = conn.cursor()		
		cur.execute("INSERT INTO vehicle (vehicle_id,tag_id,time,status) VALUES (%s,%s,%s,%s)",(vehID,epc,time,status))
		print("inserted row into vehicle table at ",datetime.datetime.now())
		conn.commit()
		cur.close()
	except:
		print("Connection attempt to Database Failed")


def callback(ch, method, properties, body):
	print(" [x] Received %r" % body)
	data = str(body).split(',')
	for key,value in dict.iteritems():
		if(data[0]==key):
			vehID = value
	if(int(data[1])==1):
		status = "IN"
#		dbInsert(vehID,data[0],data[2],status)
		print(vehID,data[0],data[2],status)
	elif(int(data[1])==2):
		status = "OUT"
#		dbInsert(vehID,data[0],data[2],status)
		print(vehID,data[0],data[2],status)

credentials = pika.PlainCredentials('amuda', 'amuda2017')
parameters = pika.ConnectionParameters('172.17.137.160',5672,'amudavhost',credentials)
connection = pika.BlockingConnection(parameters)
channel = connection.channel()
channel.queue_bind(exchange = "amq.fanout", queue = "vehicle", routing_key = None)
channel.basic_consume(callback, "vehicle",no_ack=True)
print(' [*] Waiting for messages. To exit press CTRL+C')
channel.start_consuming()



