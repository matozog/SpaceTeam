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
import java.util.Random;
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
import EngineManager.IMemberTeam;

import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

public class ServerApp implements Runnable {

	private JFrame frame;
	private ArrayList<Player> listOfPlayers;
	private ArrayList<IMemberTeam> membersTeamList = new ArrayList<IMemberTeam>();
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
	private boolean runningGame = false;
	private Thread thread;
	private Runnable run;
	private int timeToDo = 0, points = 0;
	private ArrayList<Command> listOfCommands = new ArrayList<Command>();
	private String[] statusCaptain = { "Game stopped", "Give command to the team!", "Prepare to read command!",
			"Wait for next round!", "Time for your team!" };
	private String[] listOfCommandsString = { "Set the engine to standby!", "Start turbines!",
			"Set turbo mode on engines!", "Set power the engines to 4!", "Start engine number three!", "Start engines!",
			"Check in!", "Select one option in comboBox!", "Turn off power in zone number 1!",
			"Rapair fault in room 2!", "Choose hammer from your bag!", "Turn on safe mode!",
			"Send message S.O.S to all nearby stations!", "Locate stations number 5!", "Set course on North!",
			"Set the temperature to five degrees!", "Add to the log book a stay on the Mars!", "Start autopilot!" };
	private Random random;
	private int firstCmd, secondCmd;
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
		btnKick.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					membersTeamList.get(listPlayers.getSelectedIndex()).kickPlayer();
					int i = listPlayers.getSelectedIndex();
				
					System.gc();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
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

		random = new Random();
		run = this;
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timeToDo--;
				try {
					captain.setTime(timeToDo);
					for (IMemberTeam member : membersTeamList) {
						member.setTime(timeToDo);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				textTime.setText(Integer.toString(timeToDo));
				if (timeToDo == 0) {
					timer.stop();
				}
			}
		});

		for (int i = 0; i < listOfCommandsString.length; i++) {
			Command command = null;
			if (i < 6) 
			{
				command = new Command(listOfCommandsString[i], "engine_manager", i % 6 + 1);
			} 
			else if (i >= 6 && i < 12) 
			{
				command = new Command(listOfCommandsString[i], "mechanic", i % 6 + 1);
			} 
			else if (i >= 12 && i < 18) 
			{
				command = new Command(listOfCommandsString[i], "navigator", i % 6 + 1);
			}
			listOfCommands.add(command);
		}
		listOfCommands.get(0).setResponse("active");
		listOfCommands.get(1).setResponse("2");
		listOfCommands.get(2).setResponse("Turbo");
		listOfCommands.get(3).setResponse("4");
		listOfCommands.get(4).setResponse("2");
		listOfCommands.get(5).setResponse("true");
		listOfCommands.get(7).setResponse("0");
		listOfCommands.get(8).setResponse("1");
		listOfCommands.get(9).setResponse("2");
		listOfCommands.get(10).setResponse("1");
		listOfCommands.get(11).setResponse("true");
		listOfCommands.get(12).setResponse("S.O.S");
		listOfCommands.get(13).setResponse("5");
		listOfCommands.get(14).setResponse("North");
		listOfCommands.get(15).setResponse("5");
		listOfCommands.get(16).setResponse("1");
		listOfCommands.get(17).setResponse("true");
		
	}

	public void startGame() {
		runningGame = true;
		thread = new Thread(run);
		thread.start();
	}

	@Override
	public void run() {
		while (runningGame) {
			try {
				prepareToReadCmd();
				Thread.sleep(3000);
				startRound();
				Thread.sleep(10000);
				timeToResponse();
				Thread.sleep(20000);
				endRound();
				Thread.sleep(5000);
				captain.enabledBtnStart();
			} catch (RemoteException | InterruptedException e) {
				e.printStackTrace();
			}
			runningGame = false;
		}
	}

	private void prepareToReadCmd() throws RemoteException {
		for (IMemberTeam member : membersTeamList) {
			member.setEnabledBeans(false);
		}
		captain.setGameStatus(statusCaptain[2]);
		textTime.setText(Integer.toString(3));
		lblStatus.setText(statusCaptain[2]);
		captain.setTime(3);
		timeToDo = 3;
		startTimer();
	}

	private void startRound() throws RemoteException {
		firstCmd = random.nextInt(listOfCommandsString.length);
		secondCmd = random.nextInt(listOfCommandsString.length);
		captain.setCommand(listOfCommandsString[firstCmd] + "\n"
				+ listOfCommandsString[secondCmd]);
		for (IMemberTeam member : membersTeamList) {
			member.setLblGameStatus("Watch Out! Coming commands!");
		}
		refreshGameStatus(statusCaptain[1], 8);
		startTimer();

	}

	private void timeToResponse() throws RemoteException {
		for (IMemberTeam member : membersTeamList) {
			member.setEnabledBeans(true);
			member.setLblGameStatus("Check right fields and components!");
		}
		refreshGameStatus(statusCaptain[4], 20);
		startTimer();
	}

	private void endRound() throws RemoteException {
		boolean addPoint = checkResponse();
		for (IMemberTeam member : membersTeamList) {
			member.setEnabledBeans(false);
			member.setLblGameStatus("Wait for the next round!");
			if(addPoint)
			{
				member.addPoints(1);
			}
			else member.addPoints(-1);
		}
		refreshGameStatus(statusCaptain[3], 5);
		startTimer();
		addPointToCaptain(addPoint);
	}
	
	private void refreshGameStatus(String captainStatus, int timeStatus) throws RemoteException {
		captain.setGameStatus(captainStatus);
		lblStatus.setText(captainStatus);
		textTime.setText(Integer.toString(timeStatus));
		captain.setTime(timeStatus);
		timeToDo = timeStatus;
	}
	
	private void addPointToCaptain(boolean addPoint) throws RemoteException {
		if(addPoint) {
			captain.setPoints(1);
			setTextFieldPoints(1);
		}
		else {
			captain.setPoints(-1);
			setTextFieldPoints(-1);
		}	
	}

	public boolean checkResponse() throws RemoteException 
	{
		boolean addPoint=false;
		for(Command cmd: listOfCommands)
		{
			if(wasChosenCommand(cmd))
			{
				for(int i=0; i<listOfPlayers.size();i++)
				{
					if(wasCommandSendToPlayer(cmd,listOfPlayers.get(i))){
						switch(cmd.getId())
						{
						case TextField:
							if(membersTeamList.get(i).getTextInField().equals(cmd.getResponse()))
							{
								addPoint=true;
							}
							else addPoint = false;
							break;
						case ComboBox:
							if(membersTeamList.get(i).getSelectedCombo()==Integer.parseInt(cmd.getResponse()))
							{
								addPoint=true;
							}
							else addPoint = false;
							break;
						case Slider:
							if(membersTeamList.get(i).getTextInSlider().equals(cmd.getResponse()))
							{
								addPoint=true;
							}
							else addPoint = false;
							break;
						case Spinner:
							if(membersTeamList.get(i).getVelueSpinner()==Integer.parseInt(cmd.getResponse()))
							{
								addPoint=true;
							}
							else addPoint = false;
							break;
						case List:
							if(membersTeamList.get(i).getListChoice()==Integer.parseInt(cmd.getResponse()))
							{
								addPoint=true;
							}
							else addPoint = false;
							break;
						case ToggleButton:
							if(membersTeamList.get(i).isToggleSelected()== Boolean.parseBoolean(cmd.getResponse()))
							{
								addPoint=true;
							}
							else addPoint = false;
							break;
						}
					}
				}
			}
		}
		return addPoint;
	}
	
	private boolean wasChosenCommand(Command cmd) {
		return (cmd.getCommandContent().equals(listOfCommandsString[firstCmd]) || cmd.getCommandContent().equals(listOfCommandsString[secondCmd]));
	}
	
	private boolean wasCommandSendToPlayer(Command cmd, Player player) {
		return cmd.getSendTo().equals(player.getRole());
	}

	public void startTimer() {
		timer.restart();
	}

	public void setTextFieldPoints(int point) 
	{
		points+=point;
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

	public ArrayList<IMemberTeam> getMembersTeamList() {
		return membersTeamList;
	}
	
	public String[] getListOfCommandsString() {
		return listOfCommandsString;
	}
	
	public ArrayList<Command> getListOfCommands() {
		return listOfCommands;
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
