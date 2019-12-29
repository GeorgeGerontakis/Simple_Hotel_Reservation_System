package com.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import com.functions.ManageRoomsFunctions;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.functions.mysql;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


public class ManageRooms {
	mysql mysql = new mysql();
ManageRoomsFunctions ManageRoomsFunctions = new ManageRoomsFunctions();
	private String username="";
	public JFrame frmRoomsManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageRooms window = new ManageRooms("");
					window.frmRoomsManager.setVisible(true);
				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ManageRooms(String user) {
		username = user;
		if(!(mysql.dbCheckIfUserExists(username))){
			JOptionPane.showMessageDialog(null, "You can not access this form!","Error!", JOptionPane.ERROR_MESSAGE);
			frmRoomsManager.dispose();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public String hotel="";
	public String type="";
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		
		
		frmRoomsManager = new JFrame();
		frmRoomsManager.setIconImage(Toolkit.getDefaultToolkit().getImage(ManageRooms.class.getResource("/img/hotel.png")));
		frmRoomsManager.setTitle("Rooms Manager");
		frmRoomsManager.setResizable(false);
		frmRoomsManager.setBounds(100, 100, 349, 294);
		frmRoomsManager.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmRoomsManager.getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frmRoomsManager.getSize().width;
		int h = frmRoomsManager.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		frmRoomsManager.setLocation(x, y);
		
		String[] emptyArr = {""};
		JList rooms_list = new JList();
		rooms_list.setFont(new Font("Tahoma", Font.BOLD, 13));
		rooms_list.setBounds(224, 30, 110, 158);
		frmRoomsManager.getContentPane().add(rooms_list);
		
		JList types_list = new JList();
		types_list.addListSelectionListener(new ListSelectionListener() {
			int i =0;
			public void valueChanged(ListSelectionEvent e) {
				i++;
				if(i == 2){
					rooms_list.setListData(emptyArr);
					types_list.removeAll();
				if(types_list.getSelectedValue() != null) {
					type = types_list.getSelectedValue().toString();
					rooms_list.setListData(mysql.dbGetRoomsByType(hotel, type));
				}
				i=0;
				}
			}
		});
		types_list.setFont(new Font("Tahoma", Font.BOLD, 13));
		types_list.setBounds(119, 30, 95, 158);
		frmRoomsManager.getContentPane().add(types_list);
		
		JList hotels_list = new JList();
		hotels_list.addListSelectionListener(new ListSelectionListener() {
			int i =0;
			public void valueChanged(ListSelectionEvent arg0) {
				
				i++;
				if(i == 2){
					rooms_list.setListData(emptyArr);
					types_list.removeAll();
					if(hotels_list.getSelectedValue() != null) {
					hotel=hotels_list.getSelectedValue().toString();
					types_list.setListData(mysql.dbGetRoomTypes(hotel));
					}
				i=0;
				}
			}
		});
		hotels_list.setFont(new Font("Tahoma", Font.BOLD, 13));
		hotels_list.setBounds(10, 30, 99, 158);
		frmRoomsManager.getContentPane().add(hotels_list);
		
		JLabel lblHotels = new JLabel("Hotels");
		lblHotels.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHotels.setBounds(40, 11, 46, 14);
		frmRoomsManager.getContentPane().add(lblHotels);
		
		JLabel lblRooms = new JLabel("Rooms");
		lblRooms.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRooms.setBounds(242, 11, 46, 14);
		frmRoomsManager.getContentPane().add(lblRooms);
		
		JButton btnAddHotel = new JButton("Add Hotel");
		btnAddHotel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				ManageRoomsFunctions.addHotel();
				hotels_list.removeAll();
				hotels_list.setListData(mysql.dbGetHotels());
				rooms_list.setListData(emptyArr);
				types_list.removeAll();
			}
		});
		btnAddHotel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddHotel.setBounds(10, 215, 99, 23);
		frmRoomsManager.getContentPane().add(btnAddHotel);
		
		JButton btnAddRoom = new JButton("Add Room");
		btnAddRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String number =JOptionPane.showInputDialog("Enter number of room: ");
				String sbed =JOptionPane.showInputDialog("Enter number of Single beds: ");
				String dbed =JOptionPane.showInputDialog("Enter number of Double beds: ");
				ManageRoomsFunctions.addRoom(hotels_list.getSelectedValue().toString(),number,types_list.getSelectedValue().toString(),sbed,dbed);
				hotels_list.removeAll();
				hotels_list.setListData(mysql.dbGetHotels());
				rooms_list.setListData(emptyArr);
				types_list.removeAll();
			}
		});
		btnAddRoom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddRoom.setBounds(224, 215, 110, 23);
		frmRoomsManager.getContentPane().add(btnAddRoom);
		
		JButton btnRemoveHotel = new JButton("Remove Hotel");
		btnRemoveHotel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ManageRoomsFunctions.removeHotel(hotels_list.getSelectedValue().toString());
				hotels_list.setListData(mysql.dbGetHotels());
				types_list.setListData(emptyArr);
				rooms_list.setListData(emptyArr);
			}
		});
		btnRemoveHotel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemoveHotel.setBounds(10, 238, 99, 23);
		frmRoomsManager.getContentPane().add(btnRemoveHotel);
		
		JButton btnRemoveRoom = new JButton("Remove Room");
		btnRemoveRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageRoomsFunctions.removeRoom(hotels_list.getSelectedValue().toString(),types_list.getSelectedValue().toString(),rooms_list.getSelectedValue().toString().replace("Room ","")); 
				hotels_list.removeAll();
				hotels_list.setListData(mysql.dbGetHotels());
				rooms_list.setListData(emptyArr); 
				types_list.setListData(emptyArr);
			}
		});
		btnRemoveRoom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemoveRoom.setBounds(224, 238, 110, 23);
		frmRoomsManager.getContentPane().add(btnRemoveRoom);
		
		JButton btnEditRoom = new JButton("Edit Room");
		btnEditRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String number =JOptionPane.showInputDialog("Enter new number of room: ");
				String sbed =JOptionPane.showInputDialog("Enter new number of Single beds: ");
				String dbed =JOptionPane.showInputDialog("Enter new number of Double beds: ");
		  	  Object[] possibilities = mysql.dbGetRoomTypes(hotels_list.getSelectedValue().toString());
		      String types = (String)JOptionPane.showInputDialog(null,"Select new type:","Select Hotel",JOptionPane.PLAIN_MESSAGE, null,possibilities,"ham");
		      if ((types != null) && (types.length() > 0)) {
		    	  	ManageRoomsFunctions.editRoom(hotels_list.getSelectedValue().toString(),types_list.getSelectedValue().toString(),rooms_list.getSelectedValue().toString().replace("Room ",""),number,type,sbed,dbed);
					hotels_list.removeAll();
					hotels_list.setListData(mysql.dbGetHotels());
					rooms_list.setListData(emptyArr);
					types_list.setListData(emptyArr);
		      }
			}
		});
		btnEditRoom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEditRoom.setBounds(224, 192, 110, 23);
		frmRoomsManager.getContentPane().add(btnEditRoom);
		
		JLabel lblRoomTypes = new JLabel("   R.Types");
		lblRoomTypes.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRoomTypes.setBounds(125, 11, 78, 14);
		frmRoomsManager.getContentPane().add(lblRoomTypes);
		
		
		
		hotels_list.removeAll();
		hotels_list.setListData(mysql.dbGetHotels());
		
		JButton btnAddType = new JButton("Add Type");
		btnAddType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type =JOptionPane.showInputDialog("Enter new type name: ");
				String cost =JOptionPane.showInputDialog("Enter new type cost: ");
				ManageRoomsFunctions.addType(hotels_list.getSelectedValue().toString(),type,cost);
				hotels_list.removeAll();
				hotels_list.setListData(mysql.dbGetHotels());
				rooms_list.setListData(emptyArr);
				types_list.setListData(emptyArr);
			}
		});
		btnAddType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddType.setBounds(119, 215, 95, 23);
		frmRoomsManager.getContentPane().add(btnAddType);
		
		JButton btnEditHotel = new JButton("Edit Hotel");
		btnEditHotel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String NewName =JOptionPane.showInputDialog("Enter new Hotel name: ");
				ManageRoomsFunctions.editHotel(hotels_list.getSelectedValue().toString(), NewName);
				hotels_list.removeAll();
				hotels_list.setListData(mysql.dbGetHotels());
				rooms_list.setListData(emptyArr);
				types_list.setListData(emptyArr);
			}
		});
		btnEditHotel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEditHotel.setBounds(10, 192, 99, 23);
		frmRoomsManager.getContentPane().add(btnEditHotel);
		
		JButton btnRemoveType = new JButton("Remove Type");
		btnRemoveType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageRoomsFunctions.removeType(hotels_list.getSelectedValue().toString(),types_list.getSelectedValue().toString());
				hotels_list.removeAll();
				hotels_list.setListData(mysql.dbGetHotels());
				rooms_list.setListData(emptyArr);
				types_list.setListData(emptyArr);
			}
		});
		btnRemoveType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemoveType.setBounds(119, 238, 95, 23);
		frmRoomsManager.getContentPane().add(btnRemoveType);
		
		JButton btnEditType = new JButton("Edit Type");
		btnEditType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type =JOptionPane.showInputDialog("Enter updated type name: ");
				String cost =JOptionPane.showInputDialog("Enter updated type cost: ");
				ManageRoomsFunctions.editType(hotels_list.getSelectedValue().toString(),types_list.getSelectedValue().toString(),type,cost);
				hotels_list.removeAll();
				hotels_list.setListData(mysql.dbGetHotels());
				rooms_list.setListData(emptyArr);
				types_list.setListData(emptyArr);
			}
		});
		btnEditType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEditType.setBounds(119, 192, 95, 23);
		frmRoomsManager.getContentPane().add(btnEditType);
		
		
	}
}
