package sokoModel;

public class Beacon {
	private int WIDTH = 30;
	private int HEIGHT = 30;
	private Position position;
	private ObjectType type = ObjectType.BEACON;

	public Beacon(Position position) {
		this.position = position;
	}
	
	public int getWidth() {
		return this.WIDTH;
	}
	
	public int getHeight() {
		return this.HEIGHT;
	}
	
	public ObjectType getType(){
		return this.type;
	}
	
	public Position getPosition() {
		return this.position;
	}
}
