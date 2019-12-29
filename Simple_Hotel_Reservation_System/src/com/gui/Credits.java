package com.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class Credits {
	
	 Thread music = new Thread(new Runnable() {
		    public void run()
		    {
		    	try {
					FileInputStream fileInputStream = new FileInputStream("bin/music/song.mp3");
					Player player = new Player(fileInputStream);
					player.play();
				} catch (Exception e) {
		}
		    }});  
		    

	public JFrame frmCredits;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Credits window = new Credits();
					window.frmCredits.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Credits() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public int timePassed=0;
	public int person =0;
	private void initialize() {
		music.start();
		frmCredits = new JFrame();
		frmCredits.setTitle("Credits");
		frmCredits.setIconImage(Toolkit.getDefaultToolkit().getImage(Credits.class.getResource("/img/hotel.png")));
		frmCredits.setResizable(false);
		frmCredits.setBounds(100, 100, 486, 300);
		frmCredits.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCredits.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("GEORGIOS GERONTAKIS, 43827");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 19));
		
		lblNewLabel_1.setIcon(new ImageIcon(((new ImageIcon(Credits.class.getResource("/img/george.png")).getImage().getScaledInstance(100, 100,java.awt.Image.SCALE_SMOOTH)))));
		lblNewLabel_1.setBounds(45, 37, 482, 186);
		frmCredits.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Credits.class.getResource("/img/tumblr_n2xkrxqtEG1t5q87jo1_500.gif")));
		lblNewLabel.setBounds(0, 0, 480, 271);
		frmCredits.getContentPane().add(lblNewLabel);
	
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
		    public void run() {
		       timePassed++;
		       if(timePassed == 5) {
		    	   person++;
		    	   if (person == 1) {
		    			lblNewLabel_1.setIcon(new ImageIcon(((new ImageIcon(Credits.class.getResource("/img/orestis.png")).getImage().getScaledInstance(100, 100,java.awt.Image.SCALE_SMOOTH)))));
		    			lblNewLabel_1.setText("ORESTIS PILATOS, 44129");
		    	   }else if(person == 2) {
		    			lblNewLabel_1.setIcon(new ImageIcon(((new ImageIcon(Credits.class.getResource("/img/apostolis.png")).getImage().getScaledInstance(100, 100,java.awt.Image.SCALE_SMOOTH)))));
		    			lblNewLabel_1.setText("APOSTOLIS KARAISKOS, 44011");
		    	   }else if(person == 3) {
		    			lblNewLabel_1.setIcon(new ImageIcon(((new ImageIcon(Credits.class.getResource("/img/george.png")).getImage().getScaledInstance(100, 100,java.awt.Image.SCALE_SMOOTH)))));
		    			lblNewLabel_1.setText("GEORGIOS GERONTAKIS, 43827");
		    			person=0;
		    	   }
		    	   timePassed = 0;
		       }
		    }
		 }, 0, 60*10);
		
		frmCredits.addWindowListener(new WindowAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void windowClosing(WindowEvent arg0) {
				music.stop();
				timer.cancel();
			}
		});
	}

}
