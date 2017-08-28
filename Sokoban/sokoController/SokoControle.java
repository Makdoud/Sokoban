package sokoController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

import sokoModel.*;

public class SokoControle {
	static Hashtable<Integer, Object> map = new Hashtable<Integer, Object>();
	static Hashtable <Integer, Object> beaconMap = new Hashtable<Integer, Object>();
	public static LMap levelMap = new LMap(map, beaconMap, 30, 30);
	static Beacon beacon = new Beacon(new Position());
	static int currentDoorKey;
	
	public SokoControle(LMap levelMap){
		SokoControle.levelMap = levelMap;
	}
	
	public static void checkIfAllBoxOnBeacon(){
		int numberOfBeacons = levelMap.beaconKeys.size();
		int totalBoxOnBeacon = 0;
		int currentBeaconKey = 0;
		
		ArrayList<Integer> boxKey = new ArrayList<Integer>();
		Object value = Box.class;

		for(Map.Entry <Integer, Object> entry: levelMap.map.entrySet()) {
			if (entry.getValue().getClass() == value) {
				boxKey.add((Integer) entry.getKey());
			}
		}
		
		for(Map.Entry <Integer, Object> entry: beaconMap.entrySet()) {
			levelMap.beaconKeys.add((Integer) entry.getKey());
		}
		
		if (boxKey.size()!= 0) {
		
			for (int i = 0; i < numberOfBeacons; i++) {
				currentBeaconKey = levelMap.beaconKeys.get(i);
				
				for (int j = 0; j < numberOfBeacons; j++) {
					if (boxKey.get(j) == currentBeaconKey) {
						totalBoxOnBeacon++;
					}
				}
			}
		}
		
		value = Door.class;
		
		for(Map.Entry <Integer, Object> entry : levelMap.map.entrySet()) {
			if (entry.getValue().getClass() == value) {
				currentDoorKey = (Integer) entry.getKey();
			}
		}
		
		if (totalBoxOnBeacon == numberOfBeacons) {
			Door.doorIsOpen(true);
			SokoControle.levelMap.map.remove(currentDoorKey);
		}
		else {
			Door.doorIsOpen(false);
			SokoControle.levelMap.map.put(currentDoorKey, Door.class);
		}
		
	}


	
	public static boolean isBeaconBelow (int hashKey) {
		if (levelMap.beaconMap.get(hashKey) == ObjectType.BEACON) {
			return true;
		}
		else return false;
	}
	
	public static boolean isntWall(Direction direction, Position position) {
		int hashKey = (position.getX()*100) + position.getY();
		
		if (direction != null) {
			switch(direction) {
					case UP:
						hashKey = hashKey - 1;
						break;
					case DOWN:
						hashKey = hashKey + 1;
						break;
					case LEFT:
						hashKey = hashKey - 100;
						break;
					case RIGHT:
						hashKey = hashKey + 100;
						break;
					default:
						break;
			}
		}
		
		if (SokoControle.levelMap.map.containsKey(hashKey) == false) {
			return true;
		}
		else if (SokoControle.levelMap.map.get(hashKey).getClass() == Beacon.class){
				return true;
			}
			else return false;
		
	}
	
	public void levelCreation(LMap map){
		
	}
	
	public Position nvPositionBox(Direction direction, Position position, LMap map)
	{
		int maxX = map.getMaxX();
		int posInt = positionToInt(position);
		switch (direction) {
				case UP:
					posInt -= maxX;
				break;
				
				case DOWN: 
					posInt += maxX;
				break;
				
				case LEFT:
					posInt -= 1;
				break;
				
				case RIGHT:
					posInt += 1 ;
				break;
		}
		
		return intToPosition(posInt, maxX);
	}

	public static int positionToInt(Position position) {
		return (position.getX()*100) + position.getY();
	}
	
	public Position intToPosition(int position, int maxX) {
		Position pos = new Position((position / maxX), (position % maxX));
		return pos;
	}
	
	public static Direction whichDirection(char currentChar) {
		switch (currentChar) {
		case '8':
			return Direction.UP;
		case '6':
			return Direction.RIGHT;
		case '4':
			return Direction.LEFT;
		case '2':
			return Direction.DOWN;
		default : System.out.println("Not a valid command.");
		}
		return null;
	}

	public static void createLevel(int levelNumber, LMap levelMap) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader scan;
		int maxX , maxY;
		Object nextObject = null;
		int nextChar;
		Position currentPosition = new Position();
		int nextLine;
		Class nextObjectClass = null;
		char trueChar;
		
		switch (levelNumber){
				case 1 :
						scan = new BufferedReader(new InputStreamReader(new FileInputStream("C:/Users/Makimoke/git/Sokoban/Sokoban/sokoStages/Stage1.txt"), Charset.forName("UTF-8")),9999);
						break;
				case 2 :
						scan = new BufferedReader(new InputStreamReader(new FileInputStream("C:/Users/Makimoke/git/Sokoban/Sokoban/sokoStages/Stage2.txt"), Charset.forName("UTF-8")),9999);
						break;
				default :
						System.out.println("This stage doesn't exist.");
						scan = null;
						break;
		}
		maxX = Integer.parseInt(scan.readLine());
		maxY = Integer.parseInt(scan.readLine());

		scan.skip(2);
		
		
		for (int y = 0 ; y < maxY ; y++){
			currentPosition.setX(y);
			
			for (int x = 0 ; x < maxX ; x++){
				nextChar = scan.read();
				trueChar = (char) nextChar;
				currentPosition.setY(x);
				
				switch (trueChar){
						case 'W' :
							nextObject = new Wall(new Position(x,y));
							break;
						case 'H' :
							nextObject = new Beacon(new Position(x,y));
							break;
						case 'D' : 
							nextObject = new Door(new Position(x,y));
							break;
						case 'P' : 
							nextObject = new Player(new Position(x,y), Direction.DOWN);
							break;
						case 'B' :
							nextObject = new Box(new Position(x,y));
							break;
						case 'E' : 
							nextObject = new Exit(new Position(x,y));
							break;
						case ' ' :
							nextObject = null;
							break;
				}
				
				if (nextObject != null){
					levelMap.map.put(SokoControle.positionToInt(currentPosition), nextObject);
					
					nextObjectClass = nextObject.getClass();
				}

				
				if (nextObjectClass == Beacon.class){
					levelMap.beaconMap.put(SokoControle.positionToInt(currentPosition), ObjectType.BEACON);
				}
				
				System.out.print(trueChar);
				
			}
			
			nextLine = scan.read();
			
			if (nextLine != -1){
				scan.readLine();
				System.out.println();
			}
		}
		scan.close();
	}
}
