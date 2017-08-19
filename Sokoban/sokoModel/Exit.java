package sokoModel;

public class Exit {
	private ObjectType type = ObjectType.EXIT;
	private Position position;
	
	public Exit(Position position) {
		this.position = position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public Position getPosition() {
		return this.position;
	}
}
