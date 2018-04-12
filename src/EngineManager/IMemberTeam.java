package EngineManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMemberTeam extends Remote {

	public String getPlayerNickname() throws RemoteException;
	public void kickPlayer() throws RemoteException;
	public void setLblGameStatus(String status) throws RemoteException;
	public void addPoints(int point) throws RemoteException;
	public void setTime(int time) throws RemoteException;
	public void setEnabledBeans(boolean enabled) throws RemoteException;
	public String getTextInField() throws RemoteException;
	public int getSelectedCombo() throws RemoteException;
	public String getTextInSlider() throws RemoteException;
	public int getVelueSpinner() throws RemoteException;
	public int getListChoice() throws RemoteException;
	public boolean isToggleSelected() throws RemoteException;
}
