package sokoModel;

public class Door {
	private Position position;
	private static boolean doorOpen = false;
	private ObjectType type = ObjectType.DOOR;
	
	public Door(Position position) {
		this.position = position;
	}
	
	public Position getPosition(){
		return this.position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public boolean isDoorOpen(){
		return doorOpen;
	}
	
	public static void doorIsOpen(boolean open) {
		doorOpen = open;
	}
	
	public ObjectType getType() {
		return this.type;
	}
}
