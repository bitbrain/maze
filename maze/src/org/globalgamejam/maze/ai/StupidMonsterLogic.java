package org.globalgamejam.maze.ai;

import java.util.HashMap;
import java.util.Map;

import org.globalgamejam.maze.Monster;
import org.globalgamejam.maze.MonsterLogic;
import org.globalgamejam.maze.util.Timer;

public class StupidMonsterLogic implements MonsterLogic {
	
	public static final int INTERVAL = 500;
	
	private Map<Monster, Timer> timers;
	
	public StupidMonsterLogic() {
		timers = new HashMap<Monster, Timer>();
	}

	@Override
	public void update(float delta, Monster monster) {
		
		if (timers.get(monster) == null){
			Timer timer = new Timer();
			timer.start();
			timers.put(monster, timer);
		} else {
			Timer timer = timers.get(monster);
			
			if (timer.getTicks() >= INTERVAL) {				
				moveMonster(monster);				
				timer.reset();
			}
		}
	}
	
	private void moveMonster(Monster monster) {
		
		// Check above
		
		// Check down
		
		// Check left
		
		// Check right
	}
	
}
