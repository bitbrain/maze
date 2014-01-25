package org.globalgamejam.maze.ai;

import java.util.HashMap;
import java.util.Map;

import org.globalgamejam.maze.Monster;
import org.globalgamejam.maze.MonsterLogic;
import org.globalgamejam.maze.util.Direction;
import org.globalgamejam.maze.util.RandomBag;
import org.globalgamejam.maze.util.Timer;

import com.badlogic.gdx.graphics.Color;

public class StupidMonsterLogic implements MonsterLogic {
	
	public static final int INTERVAL = 200;
	
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
				//if (monster.getColor().equals(Color.YELLOW)) {
					monster.move(calculateDirection(monster));
				//}
				timer.reset();
			}
		}
	}
	
	private Direction calculateDirection(Monster monster) {
		
		Direction direction = Direction.NONE;
		RandomBag<Direction> bag = new RandomBag<Direction>();
		
		for (Direction d : Direction.values()) {
			if (!d.equals(Direction.NONE)) {
				bag.put(d);
			}
		}
		
		bag.remove(Direction.getOpposite(monster.getDirection()));
		
		direction = bag.fetch();
		
		while (!bag.isEmpty() && !monster.canMove(direction)) {
			direction = bag.fetch();
		}
		
		if (direction.equals(Direction.NONE) || !monster.canMove(direction)) {
			// <<<<<<<<<<<<<<<< !!!_!_!_!_!_!_
			// TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			direction = Direction.getOpposite(monster.getDirection());
		}
		
		return direction;
	}
	
}
