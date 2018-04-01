package Server;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JFrame;

public class ServerApp {

	private JFrame frame;
	private ArrayList<Player> listOfPlayers;
	private CaptainFunction captainFunction;
	private Context namingContext;

	/**
	 * Initialize the contents of the frame.
	 * @throws RemoteException 
	 * @throws NamingException 
	 */
	public ServerApp() throws RemoteException, NamingException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("ServerApp");
		listOfPlayers = new ArrayList<Player>();
		
		captainFunction = new CaptainFunction();
		//Registry registry = LocateRegistry.createRegistry(10025);
		namingContext = new InitialContext();
		namingContext.rebind("rmi://localhost/captain_function", captainFunction);
		//registry.unbind(rmi://localhost:10024/captain_function);
		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerApp window = new ServerApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
