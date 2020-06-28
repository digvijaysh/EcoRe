
public class RmosvCommand implements Command {
	Rmos rmos;
	
	public RmosvCommand(Rmos rmos) {
		this.rmos = rmos;
	}

	@Override
	public void execute() {
		rmos.setVisible(true);
	}

}
