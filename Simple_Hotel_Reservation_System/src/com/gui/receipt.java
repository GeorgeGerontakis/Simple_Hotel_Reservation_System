package com.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import com.functions.receiptFunctions;

public class receipt {

public JFrame frmReceipt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					receipt window = new receipt("","","","","","","","","");
					window.frmReceipt.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public receipt(String Fname,String Lname,String hotel,String room,String price,String CheckIn,String CheckOut,String key,String email) {
		initialize(Fname,Lname,hotel,room,price,CheckIn,CheckOut,key,email);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String Fname,String Lname,String hotel,String room,String price,String CheckIn,String CheckOut,String key,String email) {
		
		String ReceiptText = "--Start of legal receipt--\n"
				 + "\n"
				 + "\n"
		         + "Customer: " + Fname + " " + Lname + "\n"
		         + "Hotel: " + hotel + "\n"
		         + "Room: " + room + "\n"
		         + "Check-In: " + CheckIn + "\n"
		         + "Check-Out: " + CheckOut + "\n"
		         + "\n"
		         + "Total cost: " + price + "\n"
		         + "\n"
		         + "Personal order key: " + key + "\n"
		         + "\n"
		         + "\n"
		         + "--End of legal receipt--\n";
		
		frmReceipt = new JFrame();
		frmReceipt.setResizable(false);
		frmReceipt.setTitle("Receipt");
		frmReceipt.setBounds(100, 100, 279, 321);
		frmReceipt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmReceipt.getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frmReceipt.getSize().width;
		int h = frmReceipt.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		frmReceipt.setLocation(x, y);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				receiptFunctions receiptFunctions = new receiptFunctions();
				if(receiptFunctions.SendEmail(email)) {
					frmReceipt.dispose();
				}
				
			}
		});
		btnOk.setBounds(0, 257, 273, 34);
		frmReceipt.getContentPane().add(btnOk);
		
		JTextArea txtReceipt = new JTextArea();
		txtReceipt.setEditable(false);
		txtReceipt.setBounds(0, 0, 273, 257);
		frmReceipt.getContentPane().add(txtReceipt);
		txtReceipt.setText(ReceiptText);
	}
}
