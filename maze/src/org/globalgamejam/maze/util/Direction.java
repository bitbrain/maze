package org.globalgamejam.maze.util;

import org.globalgamejam.maze.Monster;

public enum Direction {

	NONE,
	DOWN,
	UP,
	LEFT,
	RIGHT;
	
	public static Direction getOpposite(Direction direction) {
		switch (direction) {
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case NONE:
			return NONE;
		case RIGHT:
			return LEFT;
		case UP:
			return DOWN;
		default:
			break;
		
		}
		
		return NONE;
	}
	
	public static Direction translate(Monster monster, int newX, int newY) {
		float deltaX = newX - monster.getX();
		float deltaY = newY - monster.getY();
		
		Direction direction = Direction.NONE;
		
		if (deltaX > 0) {
			direction = Direction.RIGHT;
		} else if (deltaX < 0) {
			direction = Direction.LEFT;
		}
		
		if (deltaY > 0) {
			direction = Direction.DOWN;
		} else if (deltaY < 0) {
			direction = Direction.UP;
		}
		
		return direction;
	}
	
	public static int translateX(Direction direction, int x) {
		
		if (direction == LEFT) {
			return x - 1;
		}
		
		if (direction == RIGHT) {
			return x + 1;
		}
		
		return x;
	}
	
	public static int translateY(Direction direction, int y) {
		
		if (direction == UP) {
			return y - 1;
		}
		
		if (direction == DOWN) {
			return y + 1;
		}
		
		return y;
	}
}
