package org.globalgamejam.maze.ai;

import java.util.HashMap;
import java.util.Map;

import org.globalgamejam.maze.Block.BlockType;
import org.globalgamejam.maze.Maze;
import org.globalgamejam.maze.Monster;
import org.globalgamejam.maze.Monster.MonsterColor;
import org.globalgamejam.maze.MonsterLogic;
import org.globalgamejam.maze.util.Direction;
import org.globalgamejam.maze.util.RandomBag;
import org.globalgamejam.maze.util.Timer;

public class StupidMonsterLogic implements MonsterLogic {
	
	public static final int INTERVAL = 500;
	
	private Map<Monster, Timer> timers;
	
	private Map<Monster, MonsterColor> huntColors;
	
	private boolean first = true;
	
	public StupidMonsterLogic() {
		timers = new HashMap<Monster, Timer>();
		huntColors = new HashMap<Monster, MonsterColor>();
	}

	@Override
	public void update(float delta, Monster monster) {
		
		if (timers.get(monster) == null){
			Timer timer = new Timer();
			timer.start();
			timers.put(monster, timer);
		} 
		
		Timer timer = timers.get(monster);
			
		if (timer.getTicks() >= INTERVAL || first) {		
			moveMonster(monster);
			timer.reset();
			first = false;
		}
	}
	
	private void moveMonster(Monster monster) {

		Direction direction = calculateDirection(monster);
		Maze maze = monster.getMaze();
		int newX = Direction.translateX(direction, monster.getX());
		int newY = Direction.translateY(direction, monster.getY());
		
//		if (maze.getBlock(newX, newY).getType() ==  BlockType.MONSTER) {
//			Monster other = (Monster)maze.getBlock(newX, newY);
//			System.out.println(monster + " collides with " + other);
//			MonsterColor huntColor = huntColors.get(monster);
//			
//			if (huntColor != null && other.isColorOf(huntColor)) {
//				other.kill();
//			} else {
//				huntColors.remove(monster);
//				direction = Direction.getOpposite(direction);
//				other.setDirection(Direction.getOpposite(other.getDirection()));
//			}
//		}
		
		if (huntColors.containsKey(monster)) {
			 MonsterColor color = huntColors.get(monster);
			 MonsterColor previous = color.getPrevious();
			 
			 if (previous != null) {
				 huntColors.put(monster, color.getPrevious());
			 } else {
				 huntColors.remove(monster);
			 }
		} else if (maze.hasColor(newX, newY) && !monster.isColorOf(maze.getMonsterColor(newX, newY))) {
			MonsterColor foreignColor = maze.getMonsterColor(newX, newY);
			huntColors.put(monster, foreignColor);
		} else {
			huntColors.remove(monster);
		}
		
		if (huntColors.containsKey(monster)) {
			// HUNT YOUR F*CKING ENEMY!
			MonsterColor huntColor = huntColors.get(monster);
			Direction huntDirection = Direction.translate(monster, huntColor.getX(), huntColor.getY());
			monster.move(huntDirection);
		} else {
			monster.move(direction);
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
			direction = Direction.getOpposite(monster.getDirection());
		}
		
		return direction;
	}
	
}
