package org.globalgamejam.maze;

import org.globalgamejam.maze.util.Timer;

public class DungeonKeeper {
	
	public static long SECOND_INTERVAL = 100;
	
	public long smatch;
	
	public long maxSmatch;

	private int points;
	
	private Timer smatchTimer;
	
	public DungeonKeeper() {
		smatchTimer = new Timer();
		maxSmatch = 5000;
		smatch = maxSmatch;
	}

	public int getPoints() {
		return points;
	}
	
	public void addPoints(int points) {
		this.points += Math.abs(points);
	}
	
	public void useSmatch() {
		
		if (canSmatch()) {
			smatch -= 10f;
		}
		
		if (smatch <= 0) {
			smatch = 0;
			smatchTimer.start();
		}
	}
	
	public void update(float delta) {
		
		if (smatchTimer.getTicks() >= 300) {
			if (smatch < maxSmatch) {
				smatch += SECOND_INTERVAL;
				smatchTimer.reset();
			} else {
				smatchTimer.reset();
				smatchTimer.stop();
			}
		}
	}
	
	public float getSmatchValue() {
		return smatch / (float)maxSmatch;
	}
	
	public boolean canSmatch() {
		return !smatchTimer.isRunning();
	}
}
