package org.globalgamejam.maze;

import org.globalgamejam.maze.util.Timer;

public class DungeonKeeper {
	
	public static long SECOND_INTERVAL = 100;
	
	public long smatch;
	
	public long maxSmatch;

	private int points;
	
	private Timer smatchTimer, subTimer;
	
	private Timer playTime;
	
	public DungeonKeeper() {
		smatchTimer = new Timer();
		maxSmatch = 3000;
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
		
		if (smatchTimer.getTicks() >= 300) {
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
			smatch += 35;
					
			if (smatch > maxSmatch) {
				smatch = maxSmatch;
			}
		}
	}
	
	public long getPlayTime() {
		return playTime.getTicks();
	}
	
	public float getSmatchValue() {
		return smatch / (float)maxSmatch;
	}
	
	public boolean canSmatch() {
		return !smatchTimer.isRunning();
	}
}
