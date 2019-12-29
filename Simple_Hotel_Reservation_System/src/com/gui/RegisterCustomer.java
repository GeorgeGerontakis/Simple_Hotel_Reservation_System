package com.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.functions.RegisterCustomerFunctions;
import com.functions.mysql;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import com.functions.EasyBank;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;


public class RegisterCustomer {
	public JFrame frmRegisterCustomer;
	public String roomLabel ="";
	public String Hroom = "";
	public String hotel = "";
	public String HCheckIn ="";
	public String HCheckOut = "";
	public String Hkey = "";
	public double Hcost;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				RegisterCustomer window = new RegisterCustomer("","","","","",0);
				window.frmRegisterCustomer.setVisible(true);
			}
		});
	}
	


	/**
	 * Create the application.
	 */
	RegisterCustomerFunctions RegisterCustomerFunctions = new RegisterCustomerFunctions();
	mysql mysql = new mysql();
	EasyBank EasyBank = new EasyBank();
	private JTextField txtFname;
	private JTextField txtLname;
	private JTextField txtPhoneNumber;
	private JTextField txtMail;
	private JTextField txtCardNumber;
	private JTextField txtCSV;
	public boolean reached = false;
	private int timePassed =0;
	SearchRooms SearchRooms = new SearchRooms();

	public RegisterCustomer(String hotel,String room,String label,String CheckIn,String CheckOut,double cost){
		initialize();
		HCheckIn =CheckIn;
		HCheckOut = CheckOut;
		Hroom = room.replace("Room ","");
		roomLabel = label;
		if(mysql.dbCheckIfRoomAvailable(hotel, Hroom, HCheckIn, HCheckOut)){
			int days = (int) RegisterCustomerFunctions.daysBetween(mysql.convertDate(HCheckIn),mysql.convertDate(HCheckOut));
			Hcost = cost * days;
				JOptionPane.showMessageDialog(null, "The price for your reservation is:" + Hcost + " Euros." + System.lineSeparator() + 
				"Please note that even if you cancel your reservation AFTER completing your order, you will have to pay half of order's price!" + 
				System.lineSeparator() + "Alse note that you if you do not come at your Check-In date the cost of your reservation will not be refunded!" + 
				System.lineSeparator() +"If you do not agree please click at 'Cancel book", null, JOptionPane.INFORMATION_MESSAGE);	
			    	Hkey = RegisterCustomerFunctions.generateKey();
					mysql.dbSetRoomOnhold(hotel,Hroom,Hkey);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
			    public void run() {
			       timePassed++;
			       if(timePassed == 10) {
			    	   mysql.dbRemoveBook(Hkey);
						JOptionPane.showMessageDialog(null, "Your available time to complete reservation has expired!","Time expired!", JOptionPane.INFORMATION_MESSAGE);	
						SearchRooms.frmSearchRooms.setVisible(true);
						 timer.cancel();
						 frmRegisterCustomer.dispose();
			       }
			    }
			 }, 0, 60*1000);
		}else {
			JOptionPane.showMessageDialog(null, "You can not book this room!","ERROR!", JOptionPane.ERROR_MESSAGE);		
			SearchRooms.frmSearchRooms.setVisible(true);
			 frmRegisterCustomer.dispose();
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frmRegisterCustomer = new JFrame();
		frmRegisterCustomer.setIconImage(Toolkit.getDefaultToolkit().getImage(RegisterCustomer.class.getResource("/img/hotel.png")));
		frmRegisterCustomer.setResizable(false);
		frmRegisterCustomer.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				mysql.dbRemoveBook(Hkey);
				frmRegisterCustomer.dispose();
			}
		});
		frmRegisterCustomer.setTitle("Register Customer");
		frmRegisterCustomer.setBounds(100, 100, 437, 290);
		frmRegisterCustomer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmRegisterCustomer.getContentPane().setLayout(null);
		JLabel Lblroom = new JLabel("Thank you for choosing");
		Lblroom.setFont(new Font("Tahoma", Font.BOLD, 13));
		Lblroom.setBounds(10, 11, 369, 14);
		frmRegisterCustomer.getContentPane().add(Lblroom);
		Lblroom.setText(roomLabel);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frmRegisterCustomer.getSize().width;
		int h = frmRegisterCustomer.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		frmRegisterCustomer.setLocation(x, y);
		
		JButton btnCancel = new JButton("Cancel book");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mysql.dbRemoveBook(Hkey);
				frmRegisterCustomer.dispose();
				SearchRooms.frmSearchRooms.setVisible(true);
			}
		});
		btnCancel.setBounds(315, 227, 96, 23);
		frmRegisterCustomer.getContentPane().add(btnCancel);
		
		JLabel lblPleaseEnterYour = new JLabel("Please enter your personal information and select a payment method.");
		lblPleaseEnterYour.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPleaseEnterYour.setBounds(10, 29, 401, 23);
		frmRegisterCustomer.getContentPane().add(lblPleaseEnterYour);
		
		JLabel lblNewLabel = new JLabel("First name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(35, 63, 75, 14);
		frmRegisterCustomer.getContentPane().add(lblNewLabel);
		
		txtFname = new JTextField();
		txtFname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtFname.setBounds(108, 63, 103, 20);
		frmRegisterCustomer.getContentPane().add(txtFname);
		txtFname.setColumns(10);
		
		JComboBox comboCardType = new JComboBox();
		comboCardType.setModel(new DefaultComboBoxModel(new String[] {"", "Visa", "MasterCard"}));
		comboCardType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboCardType.setBounds(195, 169, 95, 20);
		frmRegisterCustomer.getContentPane().add(comboCardType);
		
		
		txtLname = new JTextField();
		txtLname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtLname.setColumns(10);
		txtLname.setBounds(307, 60, 104, 20);
		frmRegisterCustomer.getContentPane().add(txtLname);
		
		JLabel lblCardType = new JLabel("Card type:");
		lblCardType.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCardType.setBounds(124, 172, 75, 14);
		frmRegisterCustomer.getContentPane().add(lblCardType);
		
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLastName.setBounds(230, 63, 75, 14);
		frmRegisterCustomer.getContentPane().add(lblLastName);
		
		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPhoneNumber.setColumns(10);
		txtPhoneNumber.setBounds(110, 102, 101, 20);
		frmRegisterCustomer.getContentPane().add(txtPhoneNumber);
		
		JLabel lblPhoneNumber = new JLabel("Phone number:");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhoneNumber.setBounds(10, 105, 101, 14);
		frmRegisterCustomer.getContentPane().add(lblPhoneNumber);
		
		txtMail = new JTextField();
		txtMail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtMail.setColumns(10);
		txtMail.setBounds(309, 102, 102, 20);
		frmRegisterCustomer.getContentPane().add(txtMail);
		
		JLabel lblEmailAddress = new JLabel("Email address:");
		lblEmailAddress.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEmailAddress.setBounds(215, 105, 90, 14);
		frmRegisterCustomer.getContentPane().add(lblEmailAddress);
		
		JLabel lblCsv = new JLabel("CSV:");
		lblCsv.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCsv.setBounds(159, 199, 35, 14);
		frmRegisterCustomer.getContentPane().add(lblCsv);
		
		txtCardNumber = new JTextField();
		txtCardNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCardNumber.setColumns(10);
		txtCardNumber.setBounds(195, 141, 95, 20);
		frmRegisterCustomer.getContentPane().add(txtCardNumber);
		
		JLabel lblCardNumber = new JLabel("Card number:");
		lblCardNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCardNumber.setBounds(104, 144, 90, 14);
		frmRegisterCustomer.getContentPane().add(lblCardNumber);
		
		txtCSV = new JTextField();
		txtCSV.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCSV.setColumns(10);
		txtCSV.setBounds(195, 196, 34, 20);
		frmRegisterCustomer.getContentPane().add(txtCSV);
		
		JButton btnCompleteOrder = new JButton("Complete order");
		btnCompleteOrder.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCompleteOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((txtCardNumber.getText().compareTo("")==0) || (comboCardType.getSelectedItem() == null) || (txtCSV.getText().compareTo("")==0) || (txtPhoneNumber.getText().compareTo("")==0) || (txtMail.getText().compareTo("")==0) || (txtFname.getText().compareTo("")==0) || (txtLname.getText().compareTo("") ==0)) {
					JOptionPane.showMessageDialog(null, "Please fill all the fields in order to complete your order!","", JOptionPane.INFORMATION_MESSAGE);
				}else {
					if(EasyBank.transaction(txtCardNumber.getText(), comboCardType.getSelectedItem().toString(), txtCSV.getText(), Hcost,"receive")) {
						mysql.dbRook(hotel, Hroom, HCheckIn, HCheckOut,Hcost,txtPhoneNumber.getText(),txtMail.getText(),txtFname.getText(),txtLname.getText(),Hkey);	
						JOptionPane.showMessageDialog(null, "Your order has been completed! We will now take you to your receipt! ","Thank you for your order!", JOptionPane.INFORMATION_MESSAGE);
						frmRegisterCustomer.dispose();
						receipt receipt = new receipt(txtFname.getText(),txtLname.getText(),hotel,Hroom,Double.toString(Hcost),HCheckIn,HCheckOut,Hkey,txtMail.getText());
						receipt.frmReceipt.setVisible(true);
						}
				}
			}
		});
		btnCompleteOrder.setBounds(10, 227, 295, 23);
		frmRegisterCustomer.getContentPane().add(btnCompleteOrder);
		reached = true;
		
	}
}
