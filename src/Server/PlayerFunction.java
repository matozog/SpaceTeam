package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PlayerFunction extends UnicastRemoteObject implements IPlayerFunction {

	protected PlayerFunction() throws RemoteException {
		super();

	}

}
