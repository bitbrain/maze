package org.globalgamejam.maze;

public class DungeonKeeper {

	private int points;

	public int getPoints() {
		return points;
	}
	
	public void addPoints(int points) {
		this.points += Math.abs(points);
	}
	
	public void useSmatch() {
		
	}
	
	public float getSmatchValue() {
		return 0.0f;
	}
	
	public boolean canSmatch() {
		return false;
	}
}
