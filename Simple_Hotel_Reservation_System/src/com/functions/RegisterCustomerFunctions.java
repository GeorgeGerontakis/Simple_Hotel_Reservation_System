package com.functions;

import java.security.SecureRandom;
import java.util.Date;

public class RegisterCustomerFunctions {
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static SecureRandom rnd = new SecureRandom();
	
	public long daysBetween(Date one, Date two) {
        long difference =  (one.getTime()-two.getTime())/86400000;
        return Math.abs(difference);
    }
	
	public String generateKey() {
		 StringBuilder sb = new StringBuilder( 20 );
		   for( int i = 0; i < 20; i++ ) 
		      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		   return sb.toString();
	}
}
