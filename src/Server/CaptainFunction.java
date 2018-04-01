package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CaptainFunction extends UnicastRemoteObject implements ICaptainFunction {
	
	private int time=0, teamPoints = 0;
	private ArrayList<String> listOfCommands;
	
	protected CaptainFunction() throws RemoteException {
		super();
		
		listOfCommands = new ArrayList<String>();
		listOfCommands.add("Change power of the engine!");
	}

	@Override
	public ArrayList<Player> getPlayerList() {
		
		return null;
	}

	@Override
	public void setTime(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTeamPoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCommand() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
