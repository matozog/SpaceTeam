package Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import Captain.ICaptain;
import EngineManager.IMemberTeam;

public class CaptainFunction extends UnicastRemoteObject implements ICaptainFunction {

	private Timer time;
	private int teamPoints = 0;
	private ArrayList<Player> listOfPlayers;
	private ServerApp app;
	
	
	protected CaptainFunction(ServerApp app) throws RemoteException {
		super();
		this.app = app;
	}

	@Override
	public ArrayList<Player> getPlayerList() {

		return null;
	}

	@Override
	public void startGame() throws RemoteException {
		app.startGame();
	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub

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
