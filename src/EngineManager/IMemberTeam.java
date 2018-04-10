package EngineManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMemberTeam extends Remote {

	public String getPlayerNickname() throws RemoteException;
	public void kickPlayer() throws RemoteException;
}
