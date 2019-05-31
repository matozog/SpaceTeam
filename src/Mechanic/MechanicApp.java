package Mechanic;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import BeanClasses.MemberPanelBean;
import EngineManager.IMemberTeam;
import Server.IPlayerFunction;

public class MechanicApp extends UnicastRemoteObject implements ActionListener, IMemberTeam {

	private JFrame frame;
	private JButton btnConnectToServer,btnDisconnect;
	private IPlayerFunction playerFunction;
	private String nickname = "";
	private Server.Player player;
	private JTextField textFieldTime;
	private JTextField textFieldPoints;
	private JLabel labelStatus,lblTimeToDo,lblPoints;
	private int points = 0;
	private MemberPanelBean memberPanelBean;
	
	/**
	 * Create the application.
	 * @throws NamingException 
	 */
	public MechanicApp() throws NamingException, RemoteException {
		frame = new JFrame();
		frame.setBounds(100, 100, 488, 445);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Mechanic");
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
		
		memberPanelBean = new MemberPanelBean();
		memberPanelBean.setMinSliderValue(1);
		memberPanelBean.setMaxSliderValue(5);
		memberPanelBean.setTitlePanel("Mechanic");
		memberPanelBean.setListTitle("");
		memberPanelBean.setLblComboBox("Select random box:");
		memberPanelBean.setLblTextField("Type your name:");
		memberPanelBean.setLblToggleBtn("Emergency mode:");
		memberPanelBean.setLblSlider("Turn off power in zone:");
		memberPanelBean.setLblList("Tools:");
		memberPanelBean.setLblSpinner("Room with fault:");
		memberPanelBean.getComboBox().addItem("This");
		memberPanelBean.getComboBox().addItem("choice is");
		memberPanelBean.getComboBox().addItem("random.");
		memberPanelBean.getSlider().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				memberPanelBean.getTextFieldSpinnerValue().setText(Integer.toString(memberPanelBean.getSlider().getValue()));
			}
		});
		memberPanelBean.getModelList().addElement("Scissors");
		memberPanelBean.getModelList().addElement("Hammer");
		memberPanelBean.getModelList().addElement("Drill");
		memberPanelBean.setBounds(12, 73, 458, 296);
		frame.getContentPane().add(memberPanelBean);
		
		player = new Server.Player(nickname,"mechanic");
		
		Context namingContext = new InitialContext();
		namingContext.rebind("rmi:mechanic", this);
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
		playerFunction = null;
		System.gc();
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
					MechanicApp window = new MechanicApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
