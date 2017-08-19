package sokoModel;

import sokoController.SokoControle;

public class Mobile{
	private int HEIGHT = 30;
	private int WIDTH = 30;
	private Position position;
	
	public Mobile(Position nvPosition){
		this.position = nvPosition;
	}
	
	public Mobile(int height, int width, Position position){
		this.HEIGHT = height;
		this.WIDTH = width;
		this.position = position;
	}
	
	public void move(Direction direction, Object object){
		int hashKey = (this.position.getX()*100) + this.position.getY();
		int newHashKey = 0;
		Box box = new Box(null);
		
		if (SokoControle.isntWall(direction, this.position) == true)
		{
			
			switch (direction){
					case UP :
						newHashKey = hashKey - 1;
						this.position.setY(this.position.getY() - 1);
						break;
						
					case DOWN :
						newHashKey = hashKey + 1;
						this.position.setY(this.position.getY() + 1);
						break;
					
					case RIGHT :
						newHashKey = hashKey + 100;
						this.position.setX(this.position.getX() + 1);
						break;
					
					case LEFT :
						newHashKey = hashKey - 100;
						this.position.setX(this.position.getX() - 1);
						break;
					default:
						break;
			}
			
			SokoControle.levelMap.map.put(newHashKey, object);
			
			if (SokoControle.isBeaconBelow(hashKey) == true ) {
				SokoControle.levelMap.map.put(hashKey, SokoControle.levelMap.beaconMap.get(hashKey));
			}
			else SokoControle.levelMap.map.remove(hashKey);
			
			
			if (object.getClass() == box.getClass() ) {
				SokoControle.checkIfAllBoxOnBeacon();
			}
		}
		

	}
	
	public int getHeight(){
		return this.HEIGHT;
	}
	
	public int getWidth(){
		return this.WIDTH;
	}
	
	public Position getPosition(){
		return this.position;
	}
	
}