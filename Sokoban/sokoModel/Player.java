package sokoModel;

import sokoController.SokoControle;

public class Player extends Mobile{
	private Direction direction;
	private ObjectType type = ObjectType.PLAYER;
	
	public Player(Position position, Direction nvDirection){
		super(position);
		
		this.direction = nvDirection;
	}
	
	public void movePlayer(Direction direction, Object object) {
		
		move(direction, object);
		
		if (direction != null) {
			this.direction = direction;
		}
	}
	
	public void pushBox(Direction direction, Position position) {	
		Box box;
		int keyToMove;
		switch (direction) {
				case UP:
					keyToMove = -1;
				break;
				case DOWN:
					keyToMove = +1;
				break;
				case LEFT:
					keyToMove = -100;
				break;
				case RIGHT:
					keyToMove = +100;
				break;
				default:
					keyToMove = 0;
				break;
		}
		
		box = (Box) SokoControle.levelMap.map.getOrDefault(SokoControle.positionToInt(position)+keyToMove, null);
		
		if (box != null) {
			box.move(direction, box);
		}
	}

	public Direction getDirection() {
		return this.direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public ObjectType getType() {
		return this.type;
	}
	
}