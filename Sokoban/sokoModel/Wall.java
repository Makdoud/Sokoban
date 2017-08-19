package sokoModel;

public class Wall {
	private Position position;
	private ObjectType type = ObjectType.DOOR;
	
	public Wall(Position position) {
		this.position = position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public Position getPosition() {
		return this.position;
	}
	
	public ObjectType getType() {
		return this.type;
	}
}
