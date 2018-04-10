package Server;

import java.io.Serializable;

public class Player implements Serializable{

	private String name, role;
	private int id=0;
	
	public Player(String name, String role)
	{
		this.name=name;
		this.role=role;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
