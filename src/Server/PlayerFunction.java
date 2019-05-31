package Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Captain.ICaptain;
import EngineManager.IMemberTeam;

public class PlayerFunction extends UnicastRemoteObject implements IPlayerFunction {

	private ServerApp app;
	private String basisUrl = "rmi://localhost/";
	
	protected PlayerFunction(ServerApp app) throws RemoteException {
		super();
		this.app = app;
	}

	@Override
	public void registerPlayer(Player player) throws RemoteException {
		addPlayerToListsAndRepaintView(player);
		String url = basisUrl+player.getRole();
		try {
			IMemberTeam member = ((IMemberTeam) Naming.lookup(url));
			app.getMembersTeamList().add(member);
			if(player.getRole().equals("mechanic")){
				app.getListOfCommands().get(6).setResponse(player.getName());
			}
		} catch (MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addPlayerToListsAndRepaintView(Player player) throws RemoteException {
		app.getListOfPlayers().add(player);
		app.getModelPlayers().addElement(player.getName());
		if(app.getCaptain() != null){
			app.getCaptain().addPlayerToList(player);
		}
		app.getPanelListPlayers().repaint();
	}
	
}
