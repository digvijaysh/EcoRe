
public class RmosV {
	Command command;

	public RmosV(Command command) {
		this.command = command;
	}
	
	public void pressed() {
		command.execute();
	}
}
