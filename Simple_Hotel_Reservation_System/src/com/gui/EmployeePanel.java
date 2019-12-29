package com.gui;

import com.functions.mysql;
import java.awt.EventQueue;
import com.functions.empPanelFunctions;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmployeePanel {
	public JFrame frmEmployeePanel;
	mysql mysql = new mysql();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeePanel window = new EmployeePanel("");
					window.frmEmployeePanel.setVisible(true);
				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	empPanelFunctions EmployeePanelFunctions = new empPanelFunctions();
	private String username = "";
	public String name="";
	public EmployeePanel(String input) {
		username = input;
		if(!(mysql.dbCheckIfUserExists(username))){
			JOptionPane.showMessageDialog(null, "You can not access this form!","Error!", JOptionPane.ERROR_MESSAGE);
			frmEmployeePanel.dispose();
		}
		name = (mysql.dbGetUserInfo(username, "Fname") + " " + mysql.dbGetUserInfo(username, "Lname"));
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEmployeePanel = new JFrame();
		frmEmployeePanel.setTitle("Employee Panel");
		frmEmployeePanel.setIconImage(Toolkit.getDefaultToolkit().getImage(EmployeePanel.class.getResource("/img/hotel.png")));
		frmEmployeePanel.setResizable(false);
		frmEmployeePanel.setBounds(100, 100, 236, 155);
		frmEmployeePanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmEmployeePanel.getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frmEmployeePanel.getSize().width;
		int h = frmEmployeePanel.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		frmEmployeePanel.setLocation(x, y);
		JButton btnNewButton = new JButton("Manage reservations");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReservationsManager ReservationsManager =new ReservationsManager(username);
				ReservationsManager.frmReservationManager.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(10, 96, 210, 23);
		frmEmployeePanel.getContentPane().add(btnNewButton);
		
		JButton btnManageRooms = new JButton("Manage rooms");
		btnManageRooms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ManageRooms ManageRooms = new ManageRooms(username);
				ManageRooms.frmRoomsManager.setVisible(true);
			}
		});
		btnManageRooms.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnManageRooms.setBounds(10, 62, 210, 23);
		frmEmployeePanel.getContentPane().add(btnManageRooms);
		
		JLabel labelName = new JLabel("<name>");
		labelName.setFont(new Font("Tahoma", Font.BOLD, 15));
		labelName.setBounds(10, 28, 246, 23);
		frmEmployeePanel.getContentPane().add(labelName);
		
		
		JLabel labellUser = new JLabel("User:");
		labellUser.setFont(new Font("Tahoma", Font.BOLD, 15));
		labellUser.setBounds(10, 11, 210, 23);
		frmEmployeePanel.getContentPane().add(labellUser);
		
		JLabel labelUser = new JLabel(" ");
		labelUser.setFont(new Font("Tahoma", Font.BOLD, 15));
		labelUser.setBounds(61, 37, 218, 23);
		frmEmployeePanel.getContentPane().add(labelUser);
		labelName.setText("Name: " + name);
		labellUser.setText("  User: " + username);
	}
}
