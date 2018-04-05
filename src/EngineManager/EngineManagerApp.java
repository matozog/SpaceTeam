package EngineManager;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class EngineManagerApp {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public EngineManagerApp() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Engine Manager");
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EngineManagerApp window = new EngineManagerApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
