package com.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import com.functions.mysql;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.functions.RegisterCustomerFunctions;

public class ManagerPanel {
	mysql mysql = new mysql();
	public boolean RoomTypesSetfinished = false;
	public JFrame frmManagerPanel;
	private String SelectedUser="";
	private String SelectedHotel="";
	private String SelectedType="";
	private String SelectedCoupon="";
	private String username = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerPanel window = new ManagerPanel("");
					window.frmManagerPanel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ManagerPanel(String user) {
		username = user;
		if(!(mysql.dbCheckIfUserExists(username))){
			JOptionPane.showMessageDialog(null, "You can not access this form!","Error!", JOptionPane.ERROR_MESSAGE);
			frmManagerPanel.dispose();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		
		frmManagerPanel = new JFrame();
		frmManagerPanel.setTitle("Manager Panel");
		frmManagerPanel.setIconImage(Toolkit.getDefaultToolkit().getImage(ManagerPanel.class.getResource("/img/hotel.png")));
		frmManagerPanel.setResizable(false);
		frmManagerPanel.setBounds(100, 100, 550, 337);
		frmManagerPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frmManagerPanel.getSize().width;
		int h = frmManagerPanel.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		frmManagerPanel.setLocation(x, y);
		frmManagerPanel.getContentPane().setLayout(null);
		
		JPanel panelManageUsers = new JPanel();
		panelManageUsers.setToolTipText("");
		panelManageUsers.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, null, null, null));
		panelManageUsers.setBounds(10, 11, 168, 260);
		frmManagerPanel.getContentPane().add(panelManageUsers);
		panelManageUsers.setLayout(null);
		
		JLabel lblManageUsers = new JLabel("Manage Users");
		lblManageUsers.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblManageUsers.setBounds(27, 11, 104, 16);
		panelManageUsers.add(lblManageUsers);
		
		JList ListUsers = new JList();
		ListUsers.addListSelectionListener(new ListSelectionListener() {
			int c =0;
			public void valueChanged(ListSelectionEvent arg0) {		
				if((!(ListUsers.isSelectionEmpty())) && ListUsers.getSelectedValue() != null && (ListUsers.getSelectedValue().toString().compareTo("") != 0)) {
					c++;
					if(c == 2) {
						c=0;
					 String[] parts =ListUsers.getSelectedValue().toString().split(":");
					 SelectedUser = parts[0];
					}else {
						SelectedUser = "";
					}
				}
			}
		});
		ListUsers.setBounds(10, 38, 148, 188);
		panelManageUsers.add(ListUsers);
		
		JButton btnRemoveUser = new JButton("Remove");
		btnRemoveUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(SelectedUser.compareTo("") != 0) {
				mysql.dbRemoveUser(SelectedUser);
				ListUsers.setListData(mysql.dbGetAllUsers());
				}
			}
		});
		btnRemoveUser.setBounds(69, 226, 89, 23);
		panelManageUsers.add(btnRemoveUser);
		
		JButton btnAddUser = new JButton("Add");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Uname= JOptionPane.showInputDialog("Please enter new Username: ");
				String Pass= JOptionPane.showInputDialog("Please enter Password for user: ");
				String Fname= JOptionPane.showInputDialog("Please enter new user's First Name: ");
				String Lname= JOptionPane.showInputDialog("Please enter new user's Last Name: ");
				Object[] options = { "Manager", "Employee" };
		    	int TypeChoice = JOptionPane.showOptionDialog(null,"Choose your new user's level:","",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		    	String lvl;
		    	if (TypeChoice == JOptionPane.YES_OPTION){
		    	   lvl= "Manager";
		    	  }else {
		    		lvl="Employee";
		    	  }
		    	if(((Uname.compareTo("") != 0) && (Pass.compareTo("") != 0)) && (mysql.dbAddUser(Uname, lvl, Pass, Fname, Lname))) {
		    		JOptionPane.showMessageDialog(null, lvl + ": "+ Uname + " created!", "Finshed!",JOptionPane.INFORMATION_MESSAGE);
		    		ListUsers.setListData(mysql.dbGetAllUsers());
		    	}else {
		    		JOptionPane.showMessageDialog(null, "Could not create this user!", "Error!",JOptionPane.INFORMATION_MESSAGE);
		    	}
			}
		});
		btnAddUser.setBounds(10, 226, 58, 23);
		panelManageUsers.add(btnAddUser);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText("");
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, null, null, null));
		panel.setBounds(188, 11, 168, 260);
		frmManagerPanel.getContentPane().add(panel);
		
		JLabel lblManageDiscounts = new JLabel("Manage Discounts");
		lblManageDiscounts.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblManageDiscounts.setBounds(10, 11, 130, 16);
		panel.add(lblManageDiscounts);
		
		JList ListDiscounts = new JList();
		ListDiscounts.addListSelectionListener(new ListSelectionListener() {
			int c =0;
			public void valueChanged(ListSelectionEvent arg0) {		
				if((!(ListDiscounts.isSelectionEmpty())) && ListDiscounts.getSelectedValue() != null && (ListDiscounts.getSelectedValue().toString().compareTo("") != 0)) {
					c++;
					if(c == 2) {
						c=0;
					 String[] parts =ListDiscounts.getSelectedValue().toString().split(":");
					 SelectedHotel = parts[0];
					 SelectedType = parts[1];
					}else {
						SelectedHotel ="";
						 SelectedType ="";
					}
				}
			}
		});
		ListDiscounts.setBounds(10, 38, 148, 188);
		panel.add(ListDiscounts);
		
		JButton btnRemoveDiscount = new JButton("Remove");
		btnRemoveDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((SelectedHotel.compareTo("") != 0)&&(SelectedType.compareTo("") != 0)) {
					mysql.dbRemoveDiscount(SelectedHotel,SelectedType);
					ListDiscounts.setListData(mysql.dbGetDiscounts());
				}
			}
		});
		btnRemoveDiscount.setBounds(69, 226, 89, 23);
		panel.add(btnRemoveDiscount);
		
		JButton btnAddDiscount = new JButton("Add");
		btnAddDiscount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] possibilities = mysql.dbGetHotels();
				String hotel = (String)JOptionPane.showInputDialog(null, "Select Hotel:","Customized Dialog",JOptionPane.PLAIN_MESSAGE, null,possibilities,"");
				if ((hotel != null) && (hotel.length() > 0)) {
					Object[] options = { "Yes", "No" };
					int TypeChoice = JOptionPane.showOptionDialog(null,"Would you like to apply changes to all room types in this Hotel?","",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			    	if (TypeChoice == JOptionPane.YES_OPTION){
			    		 String discount= JOptionPane.showInputDialog("Please enter percentage of discount (example: 50):  ");
			    		if(mysql.dbAddDiscountAll(hotel,discount)) {		
				    		JOptionPane.showMessageDialog(null, "Discount added!", "Finished",JOptionPane.INFORMATION_MESSAGE);
			    		}else{
				    		JOptionPane.showMessageDialog(null, "Could not register this discount!", "Error!",JOptionPane.INFORMATION_MESSAGE);
			    		}
			    	  }else {
			    		  	Object[] possibilities2 = mysql.dbGetRoomTypes(hotel);
							String type = (String)JOptionPane.showInputDialog(null, "Select Type:","Customized Dialog",JOptionPane.PLAIN_MESSAGE, null,possibilities2,"");
							if ((type != null) && (type.length() > 0)) {
					    		 String discount= JOptionPane.showInputDialog("Please enter percentage of discount (example: 50):  ");
					    		 if(mysql.dbAddDiscountAll(hotel,discount)) {	
							    		JOptionPane.showMessageDialog(null, "Discount added!", "Finished",JOptionPane.INFORMATION_MESSAGE);
						    		}else{
							    		JOptionPane.showMessageDialog(null, "Could not register this discount!", "Error!",JOptionPane.INFORMATION_MESSAGE);
						    		}
							}
			    	  }
				    return;
				}
				
				ListDiscounts.setListData(mysql.dbGetDiscounts());
			}
		});
		btnAddDiscount.setBounds(10, 226, 58, 23);
		panel.add(btnAddDiscount);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setToolTipText("");
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, null, null, null));
		panel_1.setBounds(366, 11, 168, 260);
		frmManagerPanel.getContentPane().add(panel_1);
		
		JLabel lblManageCoupons = new JLabel("Manage Coupons");
		lblManageCoupons.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblManageCoupons.setBounds(20, 11, 130, 16);
		panel_1.add(lblManageCoupons);
		
		JList ListCoupons = new JList();
		ListCoupons.addListSelectionListener(new ListSelectionListener() {
			int c =0;
			public void valueChanged(ListSelectionEvent arg0) {		
				if((!(ListCoupons.isSelectionEmpty())) && ListCoupons.getSelectedValue() != null && (ListCoupons.getSelectedValue().toString().compareTo("") != 0)) {
					c++;
					if(c == 2) {
						c=0;
					 String[] parts =ListCoupons.getSelectedValue().toString().split(":");
					 SelectedCoupon = parts[0];
					}else {
						SelectedCoupon="";
					}
				}
			}
		});
		ListCoupons.setBounds(10, 38, 147, 188);
		panel_1.add(ListCoupons);
		
		JButton btnRemoveCoupon = new JButton("Remove");
		btnRemoveCoupon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(SelectedCoupon.compareTo("") != 0) {
				mysql.dbRemoveCoupon(SelectedCoupon);
				ListCoupons.setListData(mysql.dbGetCoupons());
			}
				}
		});
		btnRemoveCoupon.setBounds(68, 226, 89, 23);
		panel_1.add(btnRemoveCoupon);
		
		JButton btnAddCoupon = new JButton("Add");
		btnAddCoupon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "Enter a new code manually", "Generate new code" };
				int TypeChoice = JOptionPane.showOptionDialog(null,"How do you want your coupon's code created: ","",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		    	String code;
		    	if (TypeChoice == JOptionPane.YES_OPTION){
		    		code= JOptionPane.showInputDialog("Enter new coupon code: ");
		    	  }else {
			    		RegisterCustomerFunctions RegisterCustomerFunctions = new RegisterCustomerFunctions();
		    		  code = RegisterCustomerFunctions.generateKey();		 
		    	  }
	    		 String discount= JOptionPane.showInputDialog("Please enter percentage of discount (example: 50):  ");
	    		 if(code.compareTo("") != 0) {
	    		 mysql.dbAddCoupon(code, discount);
				ListCoupons.setListData(mysql.dbGetCoupons());
	    		 }
			}
		});
		btnAddCoupon.setBounds(10, 226, 58, 23);
		panel_1.add(btnAddCoupon);
		
		JButton btnNewButton_1 = new JButton("INCOME GRAPH");
		btnNewButton_1.setBounds(10, 282, 524, 23);
		frmManagerPanel.getContentPane().add(btnNewButton_1);
		
		ListUsers.setListData(mysql.dbGetAllUsers());
		ListDiscounts.setListData(mysql.dbGetDiscounts());
		ListCoupons.setListData(mysql.dbGetCoupons());
		
		
	}
}
