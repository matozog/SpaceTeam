package Server;

public class Command {

	private String commandContent;
	private String sendTo;
	private ComponentID id;
	private String response;
	private Player player;

	public Command(String commandContent, String sendTo, int id)
	{
		
		this.commandContent=commandContent;
		this.sendTo=sendTo;
		
		switch(id)
		{
		case 1:
			this.id = ComponentID.TextField;
			break;
		case 2:
			this.id = ComponentID.ComboBox;
			break;
		case 3:
			this.id = ComponentID.Slider;
			break;
		case 4:
			this.id = ComponentID.Spinner;
			break;
		case 5:
			this.id = ComponentID.List;
			break;
		case 6:
			this.id = ComponentID.ToggleButton;
			break;
		}
	}
	
	public String getCommandContent() {
		return commandContent;
	}

	public void setCommandContent(String commandContent) {
		this.commandContent = commandContent;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public ComponentID getId() {
		return id;
	}

	public void setId(ComponentID id) {
		this.id = id;
	}
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
