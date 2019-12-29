package com.functions;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import com.functions.mysql;

public class ServerFunctions {
	mysql mysql = new mysql();
	public Date convertDate(String input)  {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date;
			try {
				date = formatter.parse(input);
				return date;
			} catch (ParseException e) {
				System.out.println("Exception -> " + e);
				return null;
			}
            
	}
	DateFormat minsFormat = new SimpleDateFormat("HH:mm:ss");
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Date localDate = new Date();
	String lDay = minsFormat.format(localDate);
	public void server() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {	
		    public void run() {
		    	localDate = new Date();
		    	lDay = minsFormat.format(localDate);
		    	if(lDay.compareTo("23:59:00") == 0) {
		    		if(mysql.dbRemoveOldBooks(dateFormat.format(localDate))) {
		    		}
		    	}
		    	
		    }
		 }, 0, 60*80);
	}
}
