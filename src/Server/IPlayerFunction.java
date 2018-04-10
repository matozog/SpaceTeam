package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayerFunction extends Remote {

	public void registerPlayer(Player player) throws RemoteException;
	
}
