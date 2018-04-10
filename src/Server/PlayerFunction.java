package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PlayerFunction extends UnicastRemoteObject implements IPlayerFunction {

	private ServerApp app;
	
	protected PlayerFunction(ServerApp app) throws RemoteException {
		super();

		this.app = app;
	}

	@Override
	public void registerPlayer(Player player) throws RemoteException {
		app.getListOfPlayers().add(player);
		app.getModelPlayers().addElement(player.getName());
		if(app.getCaptain() != null)
		{
			app.getCaptain().addPlayerToList(player);
		}
		app.getPanelListPlayers().repaint();
	}
}
