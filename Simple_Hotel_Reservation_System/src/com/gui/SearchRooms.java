package com.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JOptionPane;
import com.functions.mysql;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;

public class SearchRooms {
	public JFrame frmSearchRooms;
	private JDateChooser dateUnt = new JDateChooser();
	public double cost=0;
	public double discount=0;
	public double totalCost=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchRooms window = new SearchRooms();
					window.frmSearchRooms.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SearchRooms() {
		initialize();
	}
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		
		mysql mysql = new mysql();
		frmSearchRooms = new JFrame();
		frmSearchRooms.setResizable(false);
		frmSearchRooms.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmSearchRooms.setIconImage(Toolkit.getDefaultToolkit().getImage(SearchRooms.class.getResource("/img/hotel.png")));
		frmSearchRooms.setTitle("Search Rooms");
		frmSearchRooms.setBounds(100, 100, 382, 341);
		frmSearchRooms.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSearchRooms.getContentPane().setLayout(null);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frmSearchRooms.getSize().width;
		int h = frmSearchRooms.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		frmSearchRooms.setLocation(x, y);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 40, 197, 210);
		frmSearchRooms.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel labelPrice = new JLabel("");
		labelPrice.setForeground(new Color(0, 100, 0));
		labelPrice.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelPrice.setBounds(50, 262, 304, 14);
		frmSearchRooms.getContentPane().add(labelPrice);
		
		JButton btnCoupon = new JButton("I have a coupon");
		btnCoupon.setEnabled(false);
		btnCoupon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				    String code = JOptionPane.showInputDialog("Please enter your coupon code: ");
			       	discount = mysql.dbGetCouponDiscount(code);
				if(discount != 0 ) {
						JOptionPane.showMessageDialog(null, "You have " + discount + "% discount!","You got a discount!", JOptionPane.INFORMATION_MESSAGE);
						totalCost = (discount / 100) * cost;
						totalCost = cost - totalCost;
						labelPrice.setText(totalCost + " Euros / day  ( " + cost + " Euros - " + discount + "% )");
						btnCoupon.setEnabled(false);
		       		}else {
						JOptionPane.showMessageDialog(null, "The code you entered is not correct!","Wrong code!", JOptionPane.INFORMATION_MESSAGE);
		       		}
			}
		});
		btnCoupon.setBounds(0, 287, 376, 23);
		frmSearchRooms.getContentPane().add(btnCoupon);
		
		JComboBox comboHotels = new JComboBox();
		JComboBox comboType = new JComboBox();
		comboType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((comboType.getSelectedItem() != null) && (comboHotels.getSelectedItem() != null) && (comboHotels.getSelectedItem().toString().compareTo("") != 0) && (comboType.getSelectedItem().toString().compareTo("") != 0)) {
					cost =  Integer.parseInt(mysql.dbGetRoomCost(comboHotels.getSelectedItem().toString(), comboType.getSelectedItem().toString()));					
					discount = Integer.parseInt(mysql.dbGetDiscount(comboHotels.getSelectedItem().toString(), comboType.getSelectedItem().toString()));
					if(discount != 0) {
						btnCoupon.setEnabled(false);
						totalCost = (discount / 100) * cost;
						totalCost = cost - totalCost;
						labelPrice.setText(totalCost + " Euros / day  ( " + cost + " Euros - " + discount + "% )");
					}else{
						btnCoupon.setEnabled(true);
						totalCost =cost;
						labelPrice.setText(totalCost + " Euros / day");
					}
				}
			}
		});
		comboType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboType.setToolTipText("");
		comboType.setModel(new DefaultComboBoxModel(new String[] {""}));
		comboType.setBounds(98, 33, 90, 20);
		panel.add(comboType);
		
		comboHotels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboType.removeAllItems();
				if((comboHotels.getSelectedItem() != null) && (comboHotels.getSelectedItem().toString().compareTo("") != 0)) {
					String[] types = mysql.dbGetRoomTypes(comboHotels.getSelectedItem().toString());
					for(int i = 0;i < types.length; i++) {
						comboType.addItem(types[i]);
					}
					
				}
			}
		});
		comboHotels.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboHotels.setBounds(56, 8, 132, 20);
		panel.add(comboHotels);
		
		JLabel lblRoom = new JLabel("Hotel:");
		lblRoom.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRoom.setBounds(10, 11, 46, 14);
		panel.add(lblRoom);
		
		
		JLabel lblRoomType = new JLabel("Room type:");
		lblRoomType.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRoomType.setBounds(10, 36, 84, 14);
		panel.add(lblRoomType);
		
		
		JLabel lblSingleBeds = new JLabel("Single Beds:");
		lblSingleBeds.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSingleBeds.setBounds(10, 76, 84, 14);
		panel.add(lblSingleBeds);
		
		JLabel lblDoubleBed = new JLabel("Double bed:");
		lblDoubleBed.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDoubleBed.setBounds(10, 101, 84, 14);
		panel.add(lblDoubleBed);
		
		JSpinner spinnerSingle = new JSpinner();
		spinnerSingle.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerSingle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerSingle.setBounds(98, 73, 46, 20);
		panel.add(spinnerSingle);
		
		JSpinner spinnerDouble = new JSpinner();
		spinnerDouble.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerDouble.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerDouble.setBounds(98, 98, 46, 20);
		panel.add(spinnerDouble);
		
		JLabel lblCheck = new JLabel("Check-in:");
		lblCheck.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCheck.setBounds(10, 126, 73, 14);
		panel.add(lblCheck);
		dateUnt.setDateFormatString("dd/MM/yyyy");
		dateUnt.setBounds(98, 147, 90, 14);
		panel.add(dateUnt);
		
		
		JLabel lblCheckout = new JLabel("Check-out:");
		lblCheckout.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCheckout.setBounds(10, 147, 84, 14);
		panel.add(lblCheckout);
		
		JDateChooser dateFro = new JDateChooser();
		dateFro.setDateFormatString("dd/MM/yyyy");
		dateFro.setBounds(98, 129, 89, 14);
		panel.add(dateFro);
		
		JLabel lblSearchFilters = new JLabel("Search filters");
		lblSearchFilters.setFont(new Font("Sitka Text", Font.BOLD, 14));
		lblSearchFilters.setBounds(57, 15, 101, 14);
		frmSearchRooms.getContentPane().add(lblSearchFilters);
		
		JList listRooms = new JList();
		listRooms.setForeground(Color.WHITE);
		listRooms.setFont(new Font("Tahoma", Font.PLAIN, 20));
		listRooms.setBackground(Color.GRAY);
		listRooms.setBounds(217, 40, 137, 174);
		frmSearchRooms.getContentPane().add(listRooms);
		
		JLabel lblRooms = new JLabel("Rooms");
		lblRooms.setFont(new Font("Sitka Text", Font.BOLD, 14));
		lblRooms.setBounds(268, 15, 101, 14);
		frmSearchRooms.getContentPane().add(lblRooms);
		
		comboHotels.addItem("");
		Format formatter = new SimpleDateFormat("dd/MM/yyy");
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((comboHotels.getSelectedItem().toString().compareTo("") != 0) && (comboType.getSelectedItem().toString().compareTo("") != 0) && (spinnerSingle.getValue().toString().compareTo("") != 0) && (spinnerDouble.getValue().toString().compareTo("") != 0) && (dateFro.getDate() != null) && (dateUnt.getDate() != null)) {	
				mysql mysql = new mysql();
				String From = formatter.format(dateFro.getDate());
				String Until = formatter.format(dateUnt.getDate());
				listRooms.setListData(mysql.dbGetAvailableRooms(comboHotels.getSelectedItem().toString(),comboType.getSelectedItem().toString(),(Integer)spinnerSingle.getValue(),(Integer)spinnerDouble.getValue(),From,Until));
				}else{
					JOptionPane.showMessageDialog(null, "Please enter all info needed for search.","Missing fields!.", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(10, 176, 178, 23);
		panel.add(btnNewButton);
		
		JButton btnBook = new JButton("Book");
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((listRooms.getSelectedValue() == null) || (comboHotels.getSelectedItem() == null)) {
					JOptionPane.showMessageDialog(null, "Please select a Hotel and a room in order to book.","Fields are empty!", JOptionPane.INFORMATION_MESSAGE);
			}else{
					String temp = "Thank you for choosing " + listRooms.getModel().getElementAt(listRooms.getSelectedIndex()).toString() + " in our Hotel: " + comboHotels.getSelectedItem().toString();
					RegisterCustomer RegisterCustomer =new RegisterCustomer(comboHotels.getSelectedItem().toString(),listRooms.getModel().getElementAt(listRooms.getSelectedIndex()).toString(),temp,formatter.format(dateFro.getDate()),formatter.format(dateUnt.getDate()),totalCost);
					RegisterCustomer.frmRegisterCustomer.setVisible(true);
					frmSearchRooms.dispose();
			}
			}
		});
		btnBook.setBounds(217, 225, 137, 23);
		frmSearchRooms.getContentPane().add(btnBook);
		
		JLabel lbl = new JLabel("Price: ");
		lbl.setForeground(new Color(0, 100, 0));
		lbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl.setBounds(10, 261, 41, 14);
		frmSearchRooms.getContentPane().add(lbl);
		
		
		String[] hotels = mysql.dbGetHotels();
		for(int i = 0;i < hotels.length; i++) {
			comboHotels.addItem(hotels[i]);
		}
		
	}
}
