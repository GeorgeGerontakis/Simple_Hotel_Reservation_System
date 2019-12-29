package com.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import com.functions.mysql;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class ReservationsManager {
	mysql mysql = new mysql();
	public JFrame frmReservationManager;
	public String SelectedKey="";
	private String username = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservationsManager window = new ReservationsManager("");
					window.frmReservationManager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ReservationsManager(String user) {
		username = user;
		if(!(mysql.dbCheckIfUserExists(username))){
			JOptionPane.showMessageDialog(null, "You can not access this form!","Error!", JOptionPane.ERROR_MESSAGE);
			frmReservationManager.dispose();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frmReservationManager = new JFrame();
		frmReservationManager.setTitle("Reservations Manager");
		frmReservationManager.setIconImage(Toolkit.getDefaultToolkit().getImage(ReservationsManager.class.getResource("/img/hotel.png")));
		frmReservationManager.setResizable(false);
		frmReservationManager.setBounds(100, 100, 1043, 354);
		frmReservationManager.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frmReservationManager.getSize().width;
		int h = frmReservationManager.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		frmReservationManager.setLocation(x, y);
		frmReservationManager.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("New Reservation");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchRooms SearchRooms = new SearchRooms();
				SearchRooms.frmSearchRooms.setVisible(true);
			}
		});
		btnNewButton.setBounds(10, 291, 234, 23);
		frmReservationManager.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Hotel:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(401, 4, 41, 14);
		frmReservationManager.getContentPane().add(lblNewLabel);
		
		
		JList ListBooks = new JList();
		ListBooks.addListSelectionListener(new ListSelectionListener() {
			int c=0;
			public void valueChanged(ListSelectionEvent arg0) {
				if((!(ListBooks.isSelectionEmpty())) && ListBooks.getSelectedValue() != null && (ListBooks.getSelectedValue().toString().compareTo("") != 0)) {
					c++;
					if(c == 2) {
						c=0;
					 String[] parts =ListBooks.getSelectedValue().toString().split("key:");
					 SelectedKey = parts[1];
					}
				}
			}
		});
		ListBooks.setBounds(10, 29, 1017, 251);
		frmReservationManager.getContentPane().add(ListBooks);
		
		String[] emptyArr = {};
		JComboBox comboHotels = new JComboBox();
		comboHotels.setFont(new Font("Tahoma", Font.BOLD, 13));
		comboHotels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ListBooks.setListData(emptyArr);
				if(comboHotels.getSelectedItem() != null) {
					ListBooks.setListData(mysql.dbGetBooks(comboHotels.getSelectedItem().toString()));
				}
			}
		});
		comboHotels.setBounds(441, 0, 199, 23);
		frmReservationManager.getContentPane().add(comboHotels);
		
		JButton btnConfirm = new JButton("Confirm Customer arrival");
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key =JOptionPane.showInputDialog("Enter Personal Order Key: ");
				if(mysql.dbConfirmArrival(key)) {
					JOptionPane.showMessageDialog(null, "Check In confirmed!","Done!", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Could not find book for this Code, please try again!","No book found!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnConfirm.setBounds(406, 291, 234, 23);
		frmReservationManager.getContentPane().add(btnConfirm);
		
		JButton btnRemoveReservation = new JButton("Remove reservation");
		btnRemoveReservation.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRemoveReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mysql.dbRemoveBook(SelectedKey);
				JOptionPane.showMessageDialog(null, "Removed!","Done!", JOptionPane.INFORMATION_MESSAGE);
				if(comboHotels.getSelectedItem() != null) {
					ListBooks.setListData(mysql.dbGetBooks(comboHotels.getSelectedItem().toString()));
				}
			}
		});
		btnRemoveReservation.setBounds(793, 291, 234, 23);
		frmReservationManager.getContentPane().add(btnRemoveReservation);
		
		String[] hotels = mysql.dbGetHotels();
		for(int i = 0;i < hotels.length; i++) {
			comboHotels.addItem(hotels[i]);
		}
	}
}
