package com.functions;

import javax.swing.JOptionPane;
import com.functions.mysql;

public class ManageRoomsFunctions {
	mysql mysql = new mysql();
	
	public void addHotel() {
      String enter_name= JOptionPane.showInputDialog("Please enter new hotel's name: ");
      if(mysql.dbCreateHotel(enter_name)){
			JOptionPane.showMessageDialog(null, "Hotel: " +enter_name+" created!","Finished!", JOptionPane.INFORMATION_MESSAGE);
      }else {
			JOptionPane.showMessageDialog(null, "Could not create this Hotel!","Error!", JOptionPane.ERROR_MESSAGE);
      }
	}
	
	public void removeHotel(String hotel) {
	     
	    	  	if(mysql.dbRemoveHotel(hotel)) {
	    			JOptionPane.showMessageDialog(null, "Hotel: " +hotel+" deleted!","Finished!", JOptionPane.INFORMATION_MESSAGE);
	    	  	}else {
	    			JOptionPane.showMessageDialog(null, "Could not delete this Hotel!","Error!", JOptionPane.ERROR_MESSAGE);
	    	  	}
		}
	
	public void addRoom(String hotel,String number,String type,String Sbed,String Dbed) {
	     if(mysql.dbCreateRoom(hotel, number, type, Sbed, Dbed)) {
 			JOptionPane.showMessageDialog(null, "Room " +number+" in Hotel: " + hotel +" created!","Finished!", JOptionPane.INFORMATION_MESSAGE);
	     }else {
 			JOptionPane.showMessageDialog(null, "Could not create this room!","Error!", JOptionPane.ERROR_MESSAGE);
	     }		
	}
	
	public void addType(String hotel,String type,String cost) {
		if(mysql.dbCreateType(hotel, type, cost)) {
 			JOptionPane.showMessageDialog(null, "Type: " +type+" in Hotel: " + hotel +" created!","Finished!", JOptionPane.INFORMATION_MESSAGE);
		}else {
 			JOptionPane.showMessageDialog(null, "Could not create this type!","Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void editType(String hotel,String type,String NewType,String NewCost) {
		if(mysql.dbEditType(hotel, type, NewType, NewCost)) {
 			JOptionPane.showMessageDialog(null, "Type: " +type+" in Hotel: " + hotel +" updated!","Finished!", JOptionPane.INFORMATION_MESSAGE);
		}else {
 			JOptionPane.showMessageDialog(null, "Could not update this type!","Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void editHotel (String name,String NewName){
		if(mysql.dbRenameHotel(name,NewName)) {
 			JOptionPane.showMessageDialog(null, "Hotel: "+ name +" renamed to " + NewName,"Finished!", JOptionPane.INFORMATION_MESSAGE);
		}else {
 			JOptionPane.showMessageDialog(null, "Could not rename this Hotel!","Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void removeType(String hotel,String type) {
		if(mysql.dbRemoveType(hotel,type)) {
 			JOptionPane.showMessageDialog(null, "Type: "+ type +" removed!","Finished!", JOptionPane.INFORMATION_MESSAGE);
		}else {
 			JOptionPane.showMessageDialog(null, "Could not remove this type!","Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void removeRoom(String hotel,String type,String number) {
		if(mysql.dbRemoveRoom(hotel,type,number)) {
 			JOptionPane.showMessageDialog(null, type+" Room "+ number +" in Hotel"+ hotel +" removed!","Finished!", JOptionPane.INFORMATION_MESSAGE);
		}else {
 			JOptionPane.showMessageDialog(null, "Could not remove this room!","Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void editRoom(String hotel,String type,String number,String NewNumber,String NewType,String NewSbed,String NewDbed) {
		if(mysql.dbUpdateRoom(hotel,type,number,NewNumber,NewType,NewSbed,NewDbed)) {
 			JOptionPane.showMessageDialog(null, "Room updated!","Finished!", JOptionPane.INFORMATION_MESSAGE);
		}else {
 			JOptionPane.showMessageDialog(null, "Could not update this room!","Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
