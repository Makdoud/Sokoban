package test.SokoModelTest;

import junit.framework.*;
import sokoModel.Direction;
import sokoModel.Player;
import sokoModel.Position;

public class PlayerTest extends TestCase {
	
	Position currentPosition = new Position(0,0);
	Position nextPosition = new Position();
	Player player = new Player(currentPosition, Direction.DOWN);
	
	public void testMoveLeft() throws Exception {
		nextPosition.setX(player.getPosition().getX() - 1);
		
		player.movePlayer(Direction.LEFT, player); 
		assertEquals(nextPosition.getX(), player.getPosition().getX());
		assertEquals(Direction.LEFT, player.getDirection());
	}
	
	public void testMoveRight() throws Exception {
		nextPosition.setX(player.getPosition().getX() + 1);
		
		player.movePlayer(Direction.RIGHT, player);
		assertEquals(nextPosition.getX(), player.getPosition().getX());
		assertEquals(Direction.RIGHT, player.getDirection());
	}
	
	public void testMoveDown() throws Exception {
		nextPosition.setY(player.getPosition().getY() + 1);
		
		player.movePlayer(Direction.DOWN, player);
		assertEquals(nextPosition.getY(), player.getPosition().getY());
		assertEquals(Direction.DOWN, player.getDirection());
	}
	
	public void testMoveUp() throws Exception{
		nextPosition.setY(player.getPosition().getY() - 1);
		
		player.movePlayer(Direction.UP, player);
		assertEquals(nextPosition.getY(), player.getPosition().getY());
		assertEquals(Direction.UP, player.getDirection());
	}


}