package Server;

import java.rmi.Remote;
import java.util.ArrayList;

public interface ICaptainFunction extends Remote {

	public ArrayList<Player> getPlayerList();
	
}
