package Captain;

import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Enumeration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import javax.swing.JFrame;

import Server.ICaptainFunction;


public class CaptainApp {

	private JFrame frame;
	private Context namingContext;

	/**
	 * Create the application.
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 */
	public CaptainApp() throws NamingException, RemoteException, MalformedURLException, NotBoundException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("CaptainApp");
		
		namingContext = new InitialContext();
		
		System.out.print("RMI registry bindings: ");
		Enumeration<NameClassPair> e = namingContext.list("rmi://localhost/");
		while (e.hasMoreElements())
		System.out.println(e.nextElement().getName());
		
		String url = "rmi://localhost/captain_function";
		
		ICaptainFunction cptFunction = (ICaptainFunction) Naming.lookup(url);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CaptainApp window = new CaptainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
