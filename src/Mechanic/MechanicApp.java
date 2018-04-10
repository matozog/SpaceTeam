package Mechanic;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class MechanicApp {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public MechanicApp() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Machanic App");
		frame.setResizable(false);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MechanicApp window = new MechanicApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
