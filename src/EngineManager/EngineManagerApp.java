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
import javax.swing.JPanel;
import BeanClasses.MemberPanelBean;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EngineManagerApp extends UnicastRemoteObject implements IMemberTeam, ActionListener {

	private JFrame frame;
	private JButton btnConnectToServer,btnDisconnect;
	private IPlayerFunction playerFunction;
	private String nickname = "Zuku";
	private Server.Player player;
	private MemberPanelBean memberPanelBean;
	private JTextField textFieldTime;
	private JTextField textFieldPoints;
	private JLabel labelStatus,lblTimeToDo,lblPoints;
	static EngineManagerApp window;
	private int points = 0;
	
	/**
	 * Create the application.
	 * @throws NamingException 
	 */
	public EngineManagerApp() throws RemoteException, NamingException{
		frame = new JFrame();
		frame.setBounds(100, 100, 490, 455);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Engine Manager");
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		nickname = JOptionPane.showInputDialog(frame, "Type your nickname!", "Setting nick", JOptionPane.INFORMATION_MESSAGE);
		if(nickname.length()==0) nickname = "bad nickname";
		
		btnConnectToServer = new JButton("Connect to server");
		btnConnectToServer.setBounds(180, 13, 150, 37);
		frame.getContentPane().add(btnConnectToServer);
		btnConnectToServer.addActionListener(this);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(342, 13, 128, 37);
		frame.getContentPane().add(btnDisconnect);
		
		memberPanelBean = new MemberPanelBean();
		memberPanelBean.setMinSliderValue(1);
		memberPanelBean.setMaxSliderValue(3);
		memberPanelBean.setLblToggleBtn("Start/stop engines:");
		memberPanelBean.setLblComboBox("Selected component will start:");
		memberPanelBean.setLblTextField("Engine activation (type active):");
		memberPanelBean.setLblSlider("Modes engines:");
		memberPanelBean.setLblList("Activating choosen engine:");
		memberPanelBean.setLblSpinner("Power engines:");
		memberPanelBean.setListTitle("Starting engine number:");
		memberPanelBean.setTitlePanel("Engine Manager");
		memberPanelBean.getComboBox().addItem("Nitro");
		memberPanelBean.getComboBox().addItem("Filtration exhaust");
		memberPanelBean.getComboBox().addItem("Turbines");
		memberPanelBean.setBounds(12, 63, 458, 304);
		memberPanelBean.getTextFieldSpinnerValue().setText("");
		memberPanelBean.getSlider().addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) {
				if(memberPanelBean.getSlider().getValue() == 1) memberPanelBean.getTextFieldSpinnerValue().setText("Saver");
				else if(memberPanelBean.getSlider().getValue() == 2) memberPanelBean.getTextFieldSpinnerValue().setText("Balanced");
				else if (memberPanelBean.getSlider().getValue() == 3) memberPanelBean.getTextFieldSpinnerValue().setText("Turbo");
			}
		});
		memberPanelBean.getModelList().addElement("One");
		memberPanelBean.getModelList().addElement("Two");
		memberPanelBean.getModelList().addElement("Three");
		memberPanelBean.getModelList().addElement("Four");
		frame.getContentPane().add(memberPanelBean);
		
		lblTimeToDo = new JLabel("Time to do:");
		lblTimeToDo.setBounds(12, 23, 75, 16);
		frame.getContentPane().add(lblTimeToDo);
		
		textFieldTime = new JTextField();
		textFieldTime.setBorder(new LineBorder(Color.RED, 2));
		textFieldTime.setFont(new Font("Times New Roman", Font.BOLD, 13));
		textFieldTime.setBackground(SystemColor.menu);
		textFieldTime.setBounds(93, 20, 75, 22);
		frame.getContentPane().add(textFieldTime);
		textFieldTime.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 58, 458, 2);
		frame.getContentPane().add(separator);
		
		JLabel lblGameStatus = new JLabel("Game status:");
		lblGameStatus.setBounds(12, 380, 82, 16);
		frame.getContentPane().add(lblGameStatus);
		
		labelStatus = new JLabel("");
		labelStatus.setBounds(93, 380, 202, 16);
		frame.getContentPane().add(labelStatus);
		
		lblPoints = new JLabel("Points:");
		lblPoints.setBounds(320, 380, 50, 16);
		frame.getContentPane().add(lblPoints);
		
		textFieldPoints = new JTextField();
		textFieldPoints.setEditable(false);
		textFieldPoints.setBounds(382, 377, 88, 22);
		frame.getContentPane().add(textFieldPoints);
		textFieldPoints.setColumns(10);
		
		player = new Server.Player(nickname,"engine_manager");
		
		Context namingContext = new InitialContext();
		namingContext.rebind("rmi:engine_manager", this);
		
		//setEnabledBeans(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();
		
		if(z == btnConnectToServer)
		{			
			connectToServer();
		}
		else if(z== btnDisconnect)
		{
			// not implemented yet
		}
	}
	
	private void connectToServer() {
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
	
	@Override
	public String getPlayerNickname() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void kickPlayer() throws RemoteException {
        this.window.playerFunction = null;
        window = null;
        System.gc();
        System.runFinalization();
        System.out.println("You were kicked from the server, please reconnect!");
	}
	
	@Override
	public void setLblGameStatus(String status) throws RemoteException {
		labelStatus.setText(status);
	}

	@Override
	public void addPoints(int point) throws RemoteException {
		this.points+=point;
		textFieldPoints.setText(Integer.toString(this.points));
	}
	
	@Override
	public void setTime(int time) throws RemoteException {
		textFieldTime.setText(Integer.toString(time));
	}
	
	@Override
	public void setEnabledBeans(boolean enabled) throws RemoteException {
		for(Component comp : memberPanelBean.getComponents())
		{
			comp.setEnabled(enabled);
		}
	}
	
	@Override
	public String getTextInField() throws RemoteException {
		return memberPanelBean.getTextInField();
	}

	@Override
	public int getSelectedCombo() throws RemoteException {
		// TODO Auto-generated method stub
		return memberPanelBean.getSelectedCombo();
	}

	@Override
	public String getTextInSlider() throws RemoteException {
		// TODO Auto-generated method stub
		return memberPanelBean.getTextInSlider();
	}

	@Override
	public int getVelueSpinner() throws RemoteException {
		// TODO Auto-generated method stub
		return memberPanelBean.getSpinnerValue();
	}

	@Override
	public int getListChoice() throws RemoteException {
		// TODO Auto-generated method stub
		return memberPanelBean.getSelectedListField();
	}

	@Override
	public boolean isToggleSelected() throws RemoteException {
		// TODO Auto-generated method stub
		return memberPanelBean.isToggleSelected();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new EngineManagerApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
