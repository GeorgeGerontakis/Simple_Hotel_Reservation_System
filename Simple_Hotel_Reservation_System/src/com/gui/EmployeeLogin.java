package com.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import com.functions.empPanelFunctions;
import com.functions.mysql;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class EmployeeLogin {

	public JFrame frmLogin;
	private JPasswordField txtPass;
	private JTextField txtUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeLogin window = new EmployeeLogin();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmployeeLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	empPanelFunctions EmployeePanelFunctions =new empPanelFunctions();
	
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setIconImage(Toolkit.getDefaultToolkit().getImage(EmployeeLogin.class.getResource("/img/hotel.png")));
		frmLogin.setResizable(false);
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 232, 150);
		frmLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frmLogin.getSize().width;
		int h = frmLogin.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		frmLogin.setLocation(x, y);
		JLabel lblPleaseEnterYour = new JLabel("Please enter your credentials");
		lblPleaseEnterYour.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPleaseEnterYour.setBounds(10, 11, 211, 14);
		frmLogin.getContentPane().add(lblPleaseEnterYour);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 35, 71, 14);
		frmLogin.getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPassword.setBounds(10, 60, 71, 14);
		frmLogin.getContentPane().add(lblPassword);
		
		txtPass = new JPasswordField();
		txtPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtPass.setBounds(79, 58, 127, 20);
		frmLogin.getContentPane().add(txtPass);
		
		txtUser = new JTextField();
		txtUser.setBounds(79, 32, 127, 20);
		frmLogin.getContentPane().add(txtUser);
		txtUser.setColumns(10);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				mysql mysql = new mysql();
				try {
					if(mysql.dbEmployeeConnect(txtUser.getText(), EmployeePanelFunctions.toMd5(txtPass.getText()))) {
						frmLogin.dispose();
					}
				} catch (NoSuchAlgorithmException e) {
				}
			}
		});
		btnNewButton.setBounds(10, 85, 196, 23);
		frmLogin.getContentPane().add(btnNewButton);
	}
}
