package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ICaptainFunction extends Remote {

	public ArrayList<Player> getPlayerList() throws RemoteException;
	public void setTime(int time) throws RemoteException;
	public int getTime() throws RemoteException;
	public int getTeamPoints() throws RemoteException;
	public void startGame() throws RemoteException;
	public void endGame() throws RemoteException;
	public String getCommand() throws RemoteException;
	
}
