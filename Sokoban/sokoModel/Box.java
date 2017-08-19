package sokoModel;

public class Box extends Mobile{
	
	private ObjectType type = ObjectType.BOX;
	
	public Box(Position position){
		super(position);
	}
	
	public ObjectType getType() {
		return this.type;
	}
}
