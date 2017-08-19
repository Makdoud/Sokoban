package sokoModel;

public class Position {
	private int x;
	private int y;
	
	public Position() {
		
	}
	
	public Position(int nvX, int nvY) {
		this.x = nvX;
		this.y = nvY;
	}
	
	public void setPosition(int nvX, int nvY) {
		this.x = nvX;
		this.y = nvY;
	}
	
	public void setX(int nvX){
		this.x = nvX;
	}
	
	public void setY(int nvY){
		this.y = nvY;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
}