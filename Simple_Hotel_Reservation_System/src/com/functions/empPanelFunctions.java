package com.functions;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;
import com.functions.mysql;
import com.gui.EmployeePanel;
import com.gui.ManagerPanel;


public class empPanelFunctions {

	public String toMd5(String input) throws NoSuchAlgorithmException{
		String original = input;
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(original.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	
	public void employee_login(String level,String user, String pass) {
		mysql mysql = new mysql();
		if(mysql.dbValidateUser(level, user, pass)) {
			JOptionPane.showMessageDialog(null, "Loged in as: "+ user +" ("+level+")" ,"Loged in!", JOptionPane.INFORMATION_MESSAGE);
			if(level.compareTo("Manager") == 0) {
				ManagerPanel ManagerPanel = new ManagerPanel(user);
				ManagerPanel.frmManagerPanel.setVisible(true);
			}else if(level.compareTo("Employee") == 0){
				EmployeePanel EmployeePanel = new EmployeePanel(user);
				EmployeePanel.frmEmployeePanel.setVisible(true);
			}
		}else {
	        JOptionPane.showMessageDialog(null, "Could not open Panel!","Fail!", JOptionPane.ERROR_MESSAGE);
	}
	}
}
