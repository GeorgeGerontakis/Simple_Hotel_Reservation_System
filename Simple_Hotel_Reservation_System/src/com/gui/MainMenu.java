package com.gui;
import java.awt.EventQueue;
import javax.swing.JOptionPane; 
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.functions.MainMenuFunctions;
import java.awt.Toolkit;
import com.functions.ServerFunctions;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu {
	ServerFunctions ServerFunctions =new ServerFunctions();
	Thread server = new Thread(new Runnable(){	
	   public void run()
	   {
		   ServerFunctions.server();
	   }
	});
	
	
	MainMenuFunctions MainMenuFunctions = new MainMenuFunctions();
	private JFrame frmHome;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frmHome.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		server.start(); 
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	 boolean btn_State = false;

	private void initialize() {
		JOptionPane.showMessageDialog(null, "DEAR TEACHER! for testing purposes we added two USERS:" + System.lineSeparator() + "1) Username: testEmployee  Password: 111  Level: Employee" +
		System.lineSeparator() + "2) Username: testManager  Password: 111  Level: Manager" + System.lineSeparator() + "Enjoy!","READ ME!", JOptionPane.INFORMATION_MESSAGE);
		frmHome = new JFrame();
		frmHome.addWindowListener(new WindowAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void windowClosing(WindowEvent arg0) {
				server.stop();
			}
		});
		frmHome.setIconImage(Toolkit.getDefaultToolkit().getImage(MainMenu.class.getResource("/img/hotel.png")));
		frmHome.setResizable(false);
		frmHome.setTitle("Main Menu");
		frmHome.setBounds(100, 100, 203, 229);
		frmHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHome.getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frmHome.getSize().width;
		int h = frmHome.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		frmHome.setLocation(x, y);
		
		JButton btnNewButton = new JButton("Login as Employee");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeLogin EmployeeLogin = new EmployeeLogin();
				EmployeeLogin.frmLogin.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(27, 135, 143, 23);
		frmHome.getContentPane().add(btnNewButton);
		
		JLabel lblWelcome = new JLabel("Welcome to BestHotels!");
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblWelcome.setBounds(10, 11, 184, 14);
		frmHome.getContentPane().add(lblWelcome);
		
		JButton btnMakeAReservation = new JButton("Make a reservation");
		btnMakeAReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchRooms SearchRooms = new SearchRooms();
				SearchRooms.frmSearchRooms.setVisible(true);
			}
		});
		btnMakeAReservation.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnMakeAReservation.setBounds(10, 36, 178, 23);
		frmHome.getContentPane().add(btnMakeAReservation);
		
		JButton btnCancelAReservation = new JButton("Cancel a reservation");
		btnCancelAReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String enter_key="";
				enter_key= JOptionPane.showInputDialog("Please enter your Personal Order Code: ");
					    	if(MainMenuFunctions.cancelReservation(enter_key)){
								JOptionPane.showMessageDialog(null, "Reservation Canceled!","", JOptionPane.INFORMATION_MESSAGE);
					    }
				
				
			}
		});
		btnCancelAReservation.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancelAReservation.setBounds(10, 70, 178, 23);
		frmHome.getContentPane().add(btnCancelAReservation);
		
		JButton btnCredits = new JButton("Credits");
		btnCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Credits Credits =new Credits();
				Credits.frmCredits.setVisible(true);
			}
		});
		btnCredits.setBackground(Color.CYAN);
		btnCredits.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCredits.setBounds(0, 169, 198, 33);
		frmHome.getContentPane().add(btnCredits);
		
		JButton btnReportABug = new JButton("Report a bug");
		btnReportABug.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnReportABug.setBounds(240, 240, 143, 23);
		frmHome.getContentPane().add(btnReportABug);
	}
}
