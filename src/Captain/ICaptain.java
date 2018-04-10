package Captain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;

public interface ICaptain extends Remote{
	public void setTime(int time) throws RemoteException;
	public void setPoints() throws RemoteException;
	public void setPlayerList(ArrayList<Player> listOfPlayers) throws RemoteException;
	public void addPlayerToList(Server.Player player) throws RemoteException;
	public void removePlayerFromList(Server.Player player) throws RemoteException;
	public void setCommand(String command) throws RemoteException;
	public void setGameStatus(String status) throws RemoteException;
	public void clearCommand() throws RemoteException;
	public void enabledBtnStart() throws RemoteException;
	public void repaintPlayerPanel() throws RemoteException;
	
}
