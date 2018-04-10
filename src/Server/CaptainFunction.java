package Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;

import Captain.ICaptain;

public class CaptainFunction extends UnicastRemoteObject implements ICaptainFunction, Runnable {

	private Timer time;
	private int teamPoints = 0;

	private ArrayList<Player> listOfPlayers;
	private ServerApp app;
	private boolean runningGame = false;
	private Thread thread;
	private Runnable run;
	private String[] statusCaptain = { "Game stopped", "Give command to the team!", "Prepare to read command!",
			"Wait for next round!", "Time for your team!" };
	private String[] listOfCommands = {"Rapair fault in room 2!", "Choose hammer from your bag!", "Turn off power in zone number 1!", "Turn on safe mode!",
			"Check in!", "Select one option in comboBox!", "Set the engine to standby!", "Start turbines!", "Set power the engines to 4!", "Start engines!",
			"Set turbo mode on engines!", "Start engine number three!","Start autopilot!", "Set the temperature to five degrees!", "Locate stations number 5!",
			"Add to the log book a stay on the Mars!", "Send message S.O.S to all nearby stations!"
	}; 

	protected CaptainFunction(ServerApp app) throws RemoteException {
		super();

		this.app = app;
		run = this;
	}

	@Override
	public ArrayList<Player> getPlayerList() {

		return null;
	}

	@Override
	public void startGame() throws RemoteException {
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
				app.getCaptain().enabledBtnStart();
			} catch (RemoteException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			runningGame = false;
		}
	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub

	}

	private void prepareToReadCmd() throws RemoteException {
		app.getCaptain().setGameStatus(statusCaptain[2]);
		app.setTextFieldTime(3);
		app.getLblStatus().setText(statusCaptain[2]);
		app.getCaptain().setTime(3);
		app.setTimeToDo(3);
		app.startTimer();
	}

	private void startRound() throws RemoteException {
		app.getCaptain().setGameStatus(statusCaptain[1]);
		app.getLblStatus().setText(statusCaptain[1]);
		app.setTextFieldTime(10);
		app.getCaptain().setTime(10);
		app.setTimeToDo(10);
		app.startTimer();

	}

	private void timeToResponse() throws RemoteException {
		app.getCaptain().setGameStatus(statusCaptain[4]);
		app.getLblStatus().setText(statusCaptain[4]);
		app.setTextFieldTime(20);
		app.getCaptain().setTime(20);
		app.setTimeToDo(20);
		app.startTimer();
	}

	private void endRound() throws RemoteException {
		app.getCaptain().setGameStatus(statusCaptain[3]);
		app.getLblStatus().setText(statusCaptain[3]);
		app.setTextFieldTime(5);
		app.getCaptain().setTime(5);
		app.setTimeToDo(5);
		app.startTimer();
		app.getCaptain().setPoints();
		app.setTextFieldPoints();
	}

	@Override
	public void setCaptain() throws RemoteException, MalformedURLException, NotBoundException {
		String url = "rmi://localhost/captain_app";
		app.setCaptain((ICaptain) Naming.lookup(url));
		
		for(Player p: app.getListOfPlayers())
		{
			app.getCaptain().addPlayerToList(p);
		}
	}
}
