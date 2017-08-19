package sokoModel;

import java.util.*;

public class LMap {
	boolean completed = false;
	private int maxX;
	private int maxY;
	
	public Hashtable <Integer, Object> map  = new Hashtable<Integer, Object>();
	public Hashtable <Integer, Object> beaconMap = new Hashtable<Integer, Object>();
	public ArrayList <Integer> beaconKeys = new ArrayList <Integer>();
	
	public LMap(Hashtable<Integer, Object> map, Hashtable<Integer, Object> beaconMap, int maxX, int maxY) {
		this.map = map;
		this.beaconMap = beaconMap;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	public int getMaxX() {
		return this.maxX;
	}
	
	public int getMaxY() {
		return this.maxY;
	}
}
