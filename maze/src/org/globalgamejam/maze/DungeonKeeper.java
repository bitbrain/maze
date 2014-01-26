package org.globalgamejam.maze;

import org.globalgamejam.maze.util.Timer;

public class DungeonKeeper {
	
	public static long SECOND_INTERVAL = 200;
	
	public long smatch;
	
	public long maxSmatch;

	private int points;
	
	private Timer smatchTimer, subTimer;
	
	public DungeonKeeper() {
		smatchTimer = new Timer();
		maxSmatch = 4000;
		smatch = maxSmatch;
		subTimer = new Timer();
		subTimer.start();
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
		
		if (smatchTimer.getTicks() >= 200) {
			if (smatch < maxSmatch) {
				smatch += SECOND_INTERVAL;
				
				if (smatch > maxSmatch) {
					smatch = maxSmatch;
				}
				
				smatchTimer.reset();
			} else {
				smatchTimer.reset();
				smatchTimer.stop();
			}
		}  else if (subTimer.getTicks() > 500) {
			subTimer.reset();
			smatch += 70;
					
			if (smatch > maxSmatch) {
				smatch = maxSmatch;
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
