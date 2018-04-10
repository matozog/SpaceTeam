package Server;

import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Captain.ICaptain;

import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

public class ServerApp {

	private JFrame frame;
	private ArrayList<Player> listOfPlayers;
	private CaptainFunction captainFunction;
	private PlayerFunction playerFunction;
	private Context namingContext;
	private JPanel panelListPlayers;
	private JButton btnKick;
	private JTextField textTime;
	private JLabel lblTeamPoints, lblStatus;
	private JTextField textPoints;
	private JList listPlayers;
	private DefaultListModel modelPlayers;
	private ICaptain captain;
	private Timer timer;
	private int timeToDo = 0, points=0;

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws RemoteException
	 * @throws NamingException
	 * @throws NotBoundException
	 * @throws MalformedURLException
	 */
	public ServerApp() throws RemoteException, NamingException, MalformedURLException, NotBoundException {
		frame = new JFrame();
		frame.setBounds(100, 100, 264, 343);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Server App");
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		panelListPlayers = new JPanel();
		panelListPlayers.setBorder(BorderFactory.createTitledBorder("List of players"));
		panelListPlayers.setBounds(12, 100, 222, 140);
		frame.getContentPane().add(panelListPlayers);
		panelListPlayers.setLayout(null);
		
		modelPlayers = new DefaultListModel();
		listPlayers = new JList(modelPlayers);
		listPlayers.setBackground(SystemColor.menu);
		listPlayers.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listPlayers.setLayoutOrientation(JList.VERTICAL);
		listPlayers.setVisibleRowCount(-1);
		listPlayers.setBounds(12, 25, 198, 102);
		panelListPlayers.add(listPlayers);

		btnKick = new JButton("Kick");
		btnKick.setBounds(12, 253, 222, 32);
		frame.getContentPane().add(btnKick);

		JLabel lblTime = new JLabel("Time:");
		lblTime.setBounds(12, 13, 42, 17);
		frame.getContentPane().add(lblTime);

		JLabel lblGameStatus = new JLabel("Game status:");
		lblGameStatus.setBounds(12, 43, 94, 16);
		frame.getContentPane().add(lblGameStatus);

		textTime = new JTextField();
		textTime.setEditable(false);
		textTime.setBounds(52, 10, 59, 22);
		frame.getContentPane().add(textTime);
		textTime.setColumns(10);

		lblTeamPoints = new JLabel("Points:");
		lblTeamPoints.setBounds(116, 13, 42, 16);
		frame.getContentPane().add(lblTeamPoints);

		textPoints = new JTextField();
		textPoints.setEditable(false);
		textPoints.setBounds(165, 10, 69, 22);
		frame.getContentPane().add(textPoints);
		textPoints.setText(Integer.toString(points));
		textPoints.setColumns(10);

		lblStatus = new JLabel("Game stopped!", SwingConstants.CENTER);
		lblStatus.setBounds(12, 65, 222, 22);
		frame.getContentPane().add(lblStatus);
		listOfPlayers = new ArrayList<Player>();

		captainFunction = new CaptainFunction(this);
		playerFunction = new PlayerFunction(this);
		namingContext = new InitialContext();
		namingContext.rebind("rmi:captain_function", captainFunction);
		namingContext.rebind("rmi:player_function", playerFunction);

		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timeToDo--;
				try {
					captain.setTime(timeToDo);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				textTime.setText(Integer.toString(timeToDo));
				if (timeToDo == 0) {
					timer.stop();
				}
			}
		});
	}

	public void startTimer() {
		timer.restart();
	}

	public void setTextFieldTime(int time) {
		textTime.setText(Integer.toString(time));
	}

	public void setTextFieldPoints() {
		points++;
		textPoints.setText(Integer.toString(points));
	}

	public ICaptain getCaptain() {
		return captain;
	}

	public void setCaptain(ICaptain captain) {
		this.captain = captain;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimeToDo(int timeToDo) {
		this.timeToDo = timeToDo;
	}

	public ArrayList<Player> getListOfPlayers() {
		return listOfPlayers;
	}

	public JLabel getLblStatus() {
		return lblStatus;
	}
	
	public DefaultListModel getModelPlayers() {
		return modelPlayers;
	}
	
	public JPanel getPanelListPlayers() {
		return panelListPlayers;
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
