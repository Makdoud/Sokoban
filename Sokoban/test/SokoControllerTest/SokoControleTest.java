package test.SokoControllerTest;

import java.util.Hashtable;
import java.util.Scanner;

import junit.framework.TestCase;
import sokoController.SokoControle;
import sokoModel.Beacon;
import sokoModel.Box;
import sokoModel.Direction;
import sokoModel.Door;
import sokoModel.Exit;
import sokoModel.LMap;
import sokoModel.ObjectType;
import sokoModel.Player;
import sokoModel.Position;
import sokoModel.Wall;

public class SokoControleTest extends TestCase {

	Position nextPosition = new Position();
	Player player = new Player(new Position(10,10), Direction.DOWN);
	Position boxPosition = new Position (player.getPosition().getX(), player.getPosition().getY()+1);
	Box box = new Box(boxPosition);
	Hashtable<Integer, Object> map = new Hashtable<Integer, Object>();
	Hashtable<Integer, Object> beaconMap = new Hashtable<Integer, Object>();
	SokoControle controle = new SokoControle(new LMap(map, beaconMap, 30, 30));
	
	public void testPushBoxUp() throws Exception{
		
		
		player.getPosition().setPosition(0 , 2);
		player.setDirection(Direction.UP);
		box.getPosition().setPosition(0 , 1);
		SokoControle.levelMap.map.put(2, player);
		SokoControle.levelMap.map.put(1, box);
		SokoControle.levelMap.map.put(200, ObjectType.DOOR);
		nextPosition.setPosition(box.getPosition().getX(),box.getPosition().getY()-1);
		
		box.move(player.getDirection(), box);
		boxPosition = box.getPosition();
		
		assertEquals(nextPosition.getY(), boxPosition.getY());
		assertEquals(box, SokoControle.levelMap.map.get(0));
	
		SokoControle.levelMap.map.clear();
		player = null;
		box = null;
	}
	
	public void testBoxCollisionUp() throws Exception{
		Position otherBoxPosition = new Position(0,0);
		Box otherBox = new Box(otherBoxPosition);
		player.getPosition().setPosition(0, 2);
		box.getPosition().setPosition(0, 1);
		SokoControle.levelMap.map.put(0, otherBox);
		SokoControle.levelMap.map.put(1, box);
		SokoControle.levelMap.map.put(2, player);

		box.move(Direction.UP, box);
		
		assertEquals(1, box.getPosition().getY());
		assertEquals(box, SokoControle.levelMap.map.get(1));
	
		SokoControle.levelMap.map.clear();
		box = null;
		otherBox = null;
		player = null;
	}
	
	public void testReplaceBeaconWhenBoxMoves() throws Exception{
		Beacon beacon = new Beacon(new Position(0,1));
		player.getPosition().setPosition(0,2);
		box.getPosition().setPosition(0,1);
		SokoControle.levelMap.map.put(1, box);
		SokoControle.levelMap.map.put(2, player);
		SokoControle.levelMap.beaconMap.put(SokoControle.positionToInt(beacon.getPosition()), ObjectType.BEACON);
		
		box.move(Direction.UP, box);
		
		assertEquals(0, box.getPosition().getY());
		assertEquals(box, SokoControle.levelMap.map.get(0));
		assertEquals(ObjectType.BEACON, SokoControle.levelMap.map.get(1));
		
		SokoControle.levelMap.map.clear();
		SokoControle.levelMap.beaconMap.clear();
		SokoControle.levelMap.beaconKeys.clear();
		beacon = null;
		player = null;
		box = null;
	}
	
	public void testPlayerMoveUp() throws Exception{
		player.getPosition().setPosition(0, 2);
		SokoControle.levelMap.map.put(2, player);
		nextPosition.setPosition(player.getPosition().getX(), player.getPosition().getY()-1);
		
		player.move(Direction.UP, player);
		
		assertEquals(nextPosition.getY(), player.getPosition().getY());
		assertEquals(player, SokoControle.levelMap.map.get(1));

		SokoControle.levelMap.map.clear();
		player = null;
	}
	
	public void testPlayerCollisionUp() throws Exception{
		player.getPosition().setPosition(0, 2);
		box.getPosition().setPosition(0, 1);
		SokoControle.levelMap.map.put(2, ObjectType.PLAYER);
		SokoControle.levelMap.map.put(1, ObjectType.BOX);
		
		player.move(Direction.UP, ObjectType.PLAYER);
		
		assertEquals(2, player.getPosition().getY());
		assertEquals(ObjectType.BOX, SokoControle.levelMap.map.get(1));
		assertEquals(ObjectType.PLAYER, SokoControle.levelMap.map.get(2));

		SokoControle.levelMap.map.clear();
		player = null;
		box = null;
	}
	
	public void testAllBoxOnBeaconOpenDoor() throws Exception {
		Position beaconPosition = new Position(0,0);
		Position doorPosition = new Position(2,0);
		Beacon beacon = new Beacon(beaconPosition);
		Door door = new Door(doorPosition);
		player.getPosition().setPosition(0, 2);
		box.getPosition().setPosition(0,1);
		SokoControle.levelMap.beaconMap.put(0, ObjectType.BEACON);
		SokoControle.levelMap.map.put(0, beacon);
		SokoControle.levelMap.map.put(1, box);
		SokoControle.levelMap.map.put(2, player);
		SokoControle.levelMap.map.put(200, door);
		SokoControle.levelMap.beaconKeys.add(0);
		
		box.move(Direction.UP, box);
		
		assertEquals(null, SokoControle.levelMap.map.get(200));
		
		box.move(Direction.DOWN, box);
		
		assertEquals(door.getClass(), SokoControle.levelMap.map.get(200));

		SokoControle.levelMap.beaconKeys.clear();
		SokoControle.levelMap.beaconMap.clear();
		SokoControle.levelMap.map.clear();
		beaconPosition = null;
		doorPosition = null;
		beacon = null;
		door = null;
		player = null;
		box = null;
	}
	
	public void testPlayerMoveInput() throws Exception{
		player.getPosition().setPosition(1, 1);
		SokoControle.levelMap.map.put(101, player);
		Scanner userInputs = new Scanner(System.in);
		char currentChar = userInputs.next().charAt(0);
		
		player.movePlayer(SokoControle.whichDirection(currentChar), player);
		
		switch (currentChar) {
				case '8':
					assertEquals(player, SokoControle.levelMap.map.get(100));
				break;
				
				case '6':
					assertEquals(player, SokoControle.levelMap.map.get(201));
				break;
				
				case '4':
					assertEquals(player, SokoControle.levelMap.map.get(1));
				break;
				
				case '2':
					assertEquals(player, SokoControle.levelMap.map.get(102));
				break;
				default : 
					assertEquals(player, SokoControle.levelMap.map.get(101));
		}
		
		player = null;
		SokoControle.levelMap.map.clear();
		userInputs.close();
	}
	
	public void testPlayerPushInput() throws Exception{
		Scanner userInput = new Scanner(System.in);
		Box boxUp = new Box(new Position(2,1));
		Box boxLeft = new Box(new Position(1,2));
		Box boxRight = new Box(new Position(3,2));
		box.getPosition().setPosition(2, 3);
		player.getPosition().setPosition(2, 2);
		SokoControle.levelMap.map.put(SokoControle.positionToInt(boxUp.getPosition()), boxUp);
		SokoControle.levelMap.map.put(SokoControle.positionToInt(boxLeft.getPosition()), boxLeft);
		SokoControle.levelMap.map.put(SokoControle.positionToInt(boxRight.getPosition()), boxRight);
		SokoControle.levelMap.map.put(SokoControle.positionToInt(box.getPosition()), box);
		SokoControle.levelMap.map.put(SokoControle.positionToInt(player.getPosition()), player);
		
		System.out.println("Please enter the direction you want to test pushing");
		char curChar =  userInput.next().charAt(0);
		
		switch (curChar) {
				case '8':
					player.pushBox(Direction.UP, player.getPosition());
					assertEquals(boxUp, SokoControle.levelMap.map.get(200));
					break;
		
				case '6':
					player.pushBox(Direction.RIGHT, player.getPosition());
					assertEquals(boxRight, SokoControle.levelMap.map.get(402));
					break;
		
				case '4':
					player.pushBox(Direction.LEFT, player.getPosition());
					assertEquals(boxLeft, SokoControle.levelMap.map.get(2));
					break;
			
				case '2':
					player.pushBox(Direction.DOWN, player.getPosition());
					assertEquals(box, SokoControle.levelMap.map.get(204));
					break;
				default : System.out.println("And nothing was pushed that day.");
		}
		
		userInput.close();
		boxUp = null;
		boxLeft = null;
		boxRight = null;
		box = null;
		player = null;
		SokoControle.levelMap.map.clear();
		
	}
	
	public void testLevelCreationUsingText() throws Exception{
		Hashtable<Integer,Object> mapCompare = new Hashtable<Integer,Object>();
		mapCompare.put(0, new Wall(new Position(0,0)));
		mapCompare.put(1, new Wall(new Position(0,1)));
		mapCompare.put(2, new Wall(new Position(0,2)));
		mapCompare.put(3, new Wall(new Position(0,3)));
		mapCompare.put(4, new Wall(new Position(0,4)));
		mapCompare.put(5, new Wall(new Position(0,5)));
		mapCompare.put(6, new Wall(new Position(0,6)));
		mapCompare.put(100, new Wall(new Position(1,0)));
		mapCompare.put(101, new Wall(new Position(1,1)));
		mapCompare.put(102, new Beacon(new Position(1,2)));
		mapCompare.put(107, new Wall(new Position(1,6)));
		mapCompare.put(200, new Wall(new Position(2,0)));
		mapCompare.put(201, new Beacon(new Position(2,1)));
		mapCompare.put(203, new Wall(new Position(2,3)));
		mapCompare.put(206, new Wall(new Position(2,6)));
		mapCompare.put(300, new Wall(new Position(3,0)));
		mapCompare.put(302, new Wall(new Position(3,2)));
		mapCompare.put(304, new Box(new Position(3,4)));
		mapCompare.put(306, new Wall(new Position(3,6)));
		mapCompare.put(400, new Wall(new Position(4,0)));
		mapCompare.put(403, new Box(new Position(4,3)));
		mapCompare.put(405, new Wall(new Position(4,5)));
		mapCompare.put(406, new Wall(new Position(4,6)));
		mapCompare.put(500, new Wall(new Position(5,0)));
		mapCompare.put(506, new Wall(new Position(5,6)));
		mapCompare.put(600, new Exit(new Position(6,0)));
		mapCompare.put(601, new Door(new Position(6,1)));
		mapCompare.put(604, new Player(new Position(6,4), Direction.DOWN));
		mapCompare.put(606, new Wall(new Position(6,6)));
		mapCompare.put(700, new Wall(new Position(7,0)));
		mapCompare.put(706, new Wall(new Position(7,6)));
		mapCompare.put(800, new Wall(new Position(8,0)));
		mapCompare.put(803, new Box(new Position(8,3)));
		mapCompare.put(805, new Wall(new Position(8,5)));
		mapCompare.put(806, new Wall(new Position(8,6)));
		mapCompare.put(900, new Wall(new Position(9,0)));
		mapCompare.put(902, new Wall(new Position(9,2)));
		mapCompare.put(904, new Box(new Position(9,4)));
		mapCompare.put(906, new Wall(new Position(9,6)));
		mapCompare.put(1000, new Wall(new Position(10,0)));
		mapCompare.put(1001, new Beacon(new Position(10,1)));
		mapCompare.put(1003, new Wall(new Position(10,3)));
		mapCompare.put(1006, new Wall(new Position(10,6)));
		mapCompare.put(1100, new Wall(new Position(11,0)));
		mapCompare.put(1101, new Wall(new Position(11,1)));
		mapCompare.put(1102, new Beacon(new Position(11,2)));
		mapCompare.put(1106, new Wall(new Position(11,6)));
		mapCompare.put(1200, new Wall(new Position(12,0)));
		mapCompare.put(1201, new Wall(new Position(12,1)));
		mapCompare.put(1202, new Wall(new Position(12,2)));
		mapCompare.put(1203, new Wall(new Position(12,3)));
		mapCompare.put(1204, new Wall(new Position(12,4)));
		mapCompare.put(1205, new Wall(new Position(12,5)));
		mapCompare.put(1206, new Wall(new Position(12,6)));
		
		SokoControle.createLevel(1, SokoControle.levelMap);
		
		for(int x = 0 ; x < 1300 ; x = x + 100){
			for(int y = 0; y < 7 ; y++){
				if (mapCompare.get(SokoControle.positionToInt(new Position(x,y))) != null && SokoControle.levelMap.map.get(SokoControle.positionToInt(new Position(x,y)))!= null) {
				assertEquals(mapCompare.get(SokoControle.positionToInt(new Position(x,y))).getClass(), SokoControle.levelMap.map.get(SokoControle.positionToInt(new Position(x,y))).getClass());
				}
			}
		}
	}
}
