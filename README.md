# Simple_Hotel_Reservation_System
 Semester project for Software Engineering Lab.
 
 In order to run it properly you must create the database (see BestHotels.sql) and add the correct connectionURL in mysql.java(functions package)
 
 default connectionURL is:
 
 	public String PORT = "3306";
	public String IP = "192.168.2.11:" + PORT;
	public String DATABASE = "BestHotels";
	public String USER="softEng";
	public String PASS="yolo123";

I used a university hosted git server for version controlling while developing it, and now i am just uploading it to GitHub. While i know that many security related bugs exist (like sql injection) i am not sure if i will ever patch them because it meant to be a sample aplication and not for industrial use.
