package org.globalgamejam.maze.util;

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
}
