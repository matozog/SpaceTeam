package EngineManager;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Server.IPlayerFunction;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;

public class EngineManagerApp extends UnicastRemoteObject implements IMemberTeam, ActionListener {

	private JFrame frame;
	private JButton btnConnectToServer,btnDisconnect;
	private IPlayerFunction playerFunction;
	private String nickname = "Zuku";
	private Server.Player player;

	/**
	 * Create the application.
	 * @throws NamingException 
	 */
	public EngineManagerApp() throws RemoteException, NamingException{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Engine Manager");
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		nickname = JOptionPane.showInputDialog(frame, "Type your nickname!", "Setting nick", JOptionPane.INFORMATION_MESSAGE);
		if(nickname.length()==0) nickname = "bad nickname";
		
		btnConnectToServer = new JButton("Connect to server");
		btnConnectToServer.setBounds(12, 13, 133, 37);
		frame.getContentPane().add(btnConnectToServer);
		btnConnectToServer.addActionListener(this);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(157, 13, 154, 37);
		frame.getContentPane().add(btnDisconnect);
		
		player = new Server.Player(nickname,"Engine Manager");
		
		Context namingContext = new InitialContext();
		namingContext.rebind("rmi:engine_manager", this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();
		
		if(z == btnConnectToServer)
		{
			
			try {
				String url ="rmi://localhost/player_function";
				playerFunction = (IPlayerFunction) Naming.lookup(url);
				playerFunction.registerPlayer(player);
				btnConnectToServer.setEnabled(false);
			} catch (MalformedURLException | RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Server is disable!", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(z== btnDisconnect)
		{
			
		}
	}
	
	@Override
	public String getPlayerNickname() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void kickPlayer() throws RemoteException {
		
		
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
