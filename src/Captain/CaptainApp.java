package Captain;

import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import javax.swing.JFrame;

import Server.ICaptainFunction;
import Server.Player;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Font;

public class CaptainApp extends UnicastRemoteObject implements ActionListener, ICaptain {

	private JFrame frame;
	private Context namingContext;
	private JTextField textTime, textScore;
	private JPanel panelPlayers;
	private JLabel lblScore, lblTime;
	private JButton btnStartGame, btnEndGame;
	private JTextArea textAreaCommands;
	private JList listPlayers;
	private JLabel lblStatus;
	private String[] statusCaptain = { "Game stopped", "Give command to the team!", "Prepare to read command!",
			"Wait for next round!" };
	private DefaultListModel modelPlayers;
	private ICaptainFunction cptFunction;
	private int points = 0;
	private boolean connectedToServer = false;

	/**
	 * Create the application.
	 * 
	 * @throws NotBoundException
	 * @throws MalformedURLException
	 */
	public CaptainApp() throws NamingException, RemoteException, MalformedURLException, NotBoundException {
		frame = new JFrame();
		frame.setBounds(100, 100, 393, 333);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Captain App");
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		panelPlayers = new JPanel();
		panelPlayers.setBounds(192, 70, 171, 154);
		panelPlayers.setBorder(BorderFactory.createTitledBorder("Players"));
		frame.getContentPane().add(panelPlayers);
		panelPlayers.setLayout(null);

		modelPlayers = new DefaultListModel();
		listPlayers = new JList(modelPlayers);
		listPlayers.setBounds(12, 27, 147, 114);
		listPlayers.setBackground(SystemColor.menu);
		listPlayers.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listPlayers.setLayoutOrientation(JList.VERTICAL);
		listPlayers.setVisibleRowCount(-1);
		panelPlayers.add(listPlayers);

		lblTime = new JLabel("Time to do:");
		lblTime.setBounds(12, 105, 76, 28);
		frame.getContentPane().add(lblTime);

		lblScore = new JLabel("Team points:");
		lblScore.setToolTipText("Team points:");
		lblScore.setBounds(12, 64, 95, 28);
		frame.getContentPane().add(lblScore);

		btnStartGame = new JButton("Start Game");
		btnStartGame.setBounds(12, 13, 168, 38);
		btnStartGame.addActionListener(this);
		frame.getContentPane().add(btnStartGame);

		btnEndGame = new JButton("End Game");
		btnEndGame.setBounds(195, 13, 168, 38);
		btnEndGame.addActionListener(this);
		frame.getContentPane().add(btnEndGame);

		textTime = new JTextField();
		textTime.setBounds(100, 108, 80, 22);
		textTime.setEditable(false);
		frame.getContentPane().add(textTime);
		textTime.setColumns(10);

		textScore = new JTextField();
		textScore.setText(Integer.toString(points));
		textScore.setEditable(false);
		textScore.setBounds(100, 67, 80, 22);
		frame.getContentPane().add(textScore);
		textScore.setColumns(10);

		textAreaCommands = new JTextArea();
		textAreaCommands.setFont(new Font("Times New Roman", Font.BOLD, 15));
		textAreaCommands.setForeground(new Color(0, 0, 0));
		textAreaCommands.setBackground(SystemColor.menu);
		textAreaCommands.setBounds(12, 226, 351, 61);
		frame.getContentPane().add(textAreaCommands);

		JLabel lblCommandToDo = new JLabel("Commands:");
		lblCommandToDo.setBounds(12, 197, 96, 16);
		frame.getContentPane().add(lblCommandToDo);

		lblStatus = new JLabel("Game stopped", SwingConstants.CENTER);
		lblStatus.setBounds(12, 151, 168, 33);
		frame.getContentPane().add(lblStatus);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 151, 168, 2);
		frame.getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(12, 182, 168, 2);
		frame.getContentPane().add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(9, 60, 354, 2);
		frame.getContentPane().add(separator_2);

		namingContext = new InitialContext();
		namingContext.rebind("rmi:captain_app", this);

		System.out.print("RMI registry bindings: ");
		Enumeration<NameClassPair> e = namingContext.list("rmi://localhost/");
		while (e.hasMoreElements())
			System.out.println(e.nextElement().getName());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();

		if (z == btnStartGame) {
			try {
				if (!connectedToServer) {
					try {
						String url = "rmi://localhost/captain_function";
						cptFunction = (ICaptainFunction) Naming.lookup(url);
						cptFunction.setCaptain();
						connectedToServer = true;
					} catch (MalformedURLException | NotBoundException e1) {
						JOptionPane.showMessageDialog(null, "Warning", "Could not connected to server!",
								JOptionPane.WARNING_MESSAGE);
					}
				}
				if (modelPlayers.size() != 0) {
					cptFunction.startGame();
					btnStartGame.setEnabled(false);
					btnStartGame.setText("Next turn");
				}
				else JOptionPane.showMessageDialog(null, "Not enough number of players!" );
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		} else if (z == btnEndGame) {
			btnStartGame.setText("Start game");
			resetGame();
		}

	}

	public void resetGame()
	{
		
	}
	
	@Override
	public void setTime(int time) {
		textTime.setText(Integer.toString(time));
	}

	@Override
	public void setPoints(int point) {
		this.points+=point;
		textScore.setText(Integer.toString(this.points));

	}

	@Override
	public void setPlayerList(ArrayList<Player> listOfPlayers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPlayerToList(Server.Player player) {
		modelPlayers.addElement(player.getName());
	}

	@Override
	public void removePlayerFromList(Server.Player player) throws RemoteException {
		modelPlayers.removeElement(player.getName());

	}

	@Override
	public void setCommand(String command) {
		textAreaCommands.setText(command);

	}

	@Override
	public void setGameStatus(String status) {
		lblStatus.setText(status);

	}

	@Override
	public void repaintPlayerPanel() throws RemoteException {
		panelPlayers.repaint();

	}

	@Override
	public void clearCommand() {
		textAreaCommands.setText("");
	}

	@Override
	public void enabledBtnStart() throws RemoteException {
		btnStartGame.setEnabled(true);
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
