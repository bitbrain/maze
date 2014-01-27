package org.globalgamejam.maze.ai;

import java.util.HashMap;
import java.util.Map;

import org.globalgamejam.maze.Block;
import org.globalgamejam.maze.Block.BlockType;
import org.globalgamejam.maze.Maze;
import org.globalgamejam.maze.Monster;
import org.globalgamejam.maze.Monster.MonsterColor;
import org.globalgamejam.maze.MonsterLogic;
import org.globalgamejam.maze.util.Direction;
import org.globalgamejam.maze.util.RandomBag;
import org.globalgamejam.maze.util.Timer;

public class StupidMonsterLogic implements MonsterLogic {
	
	public static Map<Monster, Boolean> manualMove = new HashMap<Monster, Boolean>();
	
	public static final float INTERVAL = 0.7f;
	
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
		
		float interval = monster.getInterval();
			
		if (timer.getTicks() >= (interval * 1000) || first) {		
			moveMonster(monster);
			timer.reset();
			first = false;
		}
	}
	
	private void moveMonster(Monster monster) {

		if (!monster.isDead() && (!manualMove.containsKey(monster) || !manualMove.get(monster))) {
			
			Direction direction = calculateDirection(monster);			
			
			Maze maze = monster.getMaze();
			int newX = Direction.translateX(direction, monster.getX());
			int newY = Direction.translateY(direction, monster.getY());
			
			Direction otherRootDirection = Direction.getOpposite(direction);
			
			int anotherX = Direction.translateX(otherRootDirection, monster.getX());
			int anotherY = Direction.translateY(otherRootDirection, monster.getY());
			
			Block next = maze.getBlock(newX, newY);
			
			if (next != null && next.getType() != BlockType.MONSTER) {
				next = maze.getBlock(anotherX, anotherY);
			}
			
			if (next != null && next.getType() == BlockType.MONSTER) {

				Monster other = (Monster)next;
				
				if (monster.isAngry()) {
					monster.setAngry(false);
					huntColors.remove(monster);					
					other.kill();
					monster.move(direction);
					return;
				} else if (!other.isAngry()) {					
					Direction otherDirection = Direction.getOpposite(other.getDirection());
					other.move(otherDirection);
					monster.setDirection(direction);
					manualMove.put(other, true);
					return;
				}
				
			}
			
			if (huntColors.containsKey(monster)) {
				 MonsterColor color = huntColors.get(monster);
				 MonsterColor previous = color.getPrevious();
				 
				 if (previous != null) {
					 // CHeck if next color is available
					 Direction d = Direction.translate(monster, previous.getX(), previous.getY());
					 int colorX = Direction.translateX(d, monster.getX());
					 int colorY = Direction.translateY(d, monster.getY());
					 
					 MonsterColor nextColor = monster.getMaze().getMonsterColor(colorX, colorY);
					 
					 if (nextColor != null && !monster.isColorOf(nextColor)) {
						 huntColors.put(monster, color.getPrevious());
						 monster.setAngry(true);
					 } else {
						 huntColors.remove(monster);
						 monster.setAngry(false);
					 }
				 } else {
					 huntColors.remove(monster);
					 monster.setAngry(false);
				 }
			} else if (maze.hasColor(newX, newY) && !monster.isColorOf(maze.getMonsterColor(newX, newY))) {
				MonsterColor foreignColor = maze.getMonsterColor(newX, newY);
				huntColors.put(monster, foreignColor);
				monster.setAngry(true);
			} else {
				huntColors.remove(monster);
				monster.setAngry(false);
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
		
		if (manualMove.containsKey(monster)) {
			manualMove.put(monster, false);
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
		bag.put(monster.getDirection());
		bag.put(monster.getDirection());
		bag.put(monster.getDirection());
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