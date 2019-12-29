package com.functions;
import javax.swing.JOptionPane;
import com.functions.mysql;
import com.functions.EasyBank;


public class MainMenuFunctions {
	
	mysql mysql = new mysql();
	EasyBank EasyBank =new EasyBank();
	
	
	
	
	public boolean cancelReservation(String key) {
		if(mysql.dbCheckIfBookExists(key)){
					int response = JOptionPane.showConfirmDialog(null, "Are you sure that you want to cancel your reservation with Order Code:" +key + " ?" + System.lineSeparator() + "Please note that only the half of your order's total cost ("+ (mysql.dbGetBookCost(key)/2) +" Euros)  will be refunded!", "Confirm",
				     JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				    if (response == JOptionPane.NO_OPTION) {
				    	return false;
				    } else if (response == JOptionPane.YES_OPTION){
				    	String CardNumber = JOptionPane.showInputDialog("Please enter your CardNumber:");
				    	Object[] options = { "Visa", "MasterCard" };
				    	int TypeChoice = JOptionPane.showOptionDialog(null,"Choose your card type:","",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				    	String type;
				    	if (TypeChoice == JOptionPane.YES_OPTION){
				    	   type = "Visa";
				    	  }else {
				    		type="MasterCard";
				    	  }
				    	String csv = JOptionPane.showInputDialog("Please enter your cards's CSV: ");
				    	EasyBank.transaction(CardNumber, type, csv, (mysql.dbGetBookCost(key)/2), "refund");
				    	mysql.dbRemoveBook(key);
				    	return true;
				    }else {
				    	return false;
				    }
				    }else {
						JOptionPane.showMessageDialog(null, "There is no resevation associated with this Personal Order Key!","No reservation found!", JOptionPane.INFORMATION_MESSAGE);
						return false;
				    }
		}
	}
	

