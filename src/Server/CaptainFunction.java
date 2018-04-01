package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CaptainFunction extends UnicastRemoteObject implements ICaptainFunction {

	protected CaptainFunction() throws RemoteException {
		super();
		
	}

	@Override
	public ArrayList<Player> getPlayerList() {
		
		return null;
	}

}
