package Server;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ICaptainFunction extends Remote {

	public ArrayList<Player> getPlayerList() throws RemoteException;
	public void startGame() throws RemoteException;
	public void endGame() throws RemoteException;
	public void setCaptain() throws RemoteException, MalformedURLException, NotBoundException;
	
}
