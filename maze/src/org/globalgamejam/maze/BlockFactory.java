package org.globalgamejam.maze;

import org.globalgamejam.maze.Block.BlockType;
import org.globalgamejam.maze.ai.StupidMonsterLogic;

import com.badlogic.gdx.graphics.Color;

public class BlockFactory {
	
	public static final char GREEN = 'g';
	public static final char YELLOW = 'y';
	public static final char BLUE = 'b';
	public static final char RED = 'r';
	public static final char AIR = '0';
	public static final char WALL = '1';
	
	private Maze maze;
	
	public BlockFactory(Maze maze) {
		this.maze = maze;
	}

	public Block create(char c, int x, int y) {
		
		Block block = null;
		
		switch (c) {
			case GREEN:
				block = new Monster(x, y, maze, new StupidMonsterLogic());
				block.setColor(Color.valueOf("90ff00"));
				break;
			case YELLOW:
				block = new Monster(x, y, maze, new StupidMonsterLogic());
				block.setColor(Color.valueOf("ffc000"));
				break;
			case BLUE:
				block = new Monster(x, y, maze, new StupidMonsterLogic());
				block.setColor(Color.valueOf("00b4ff"));
				break;
			case RED:
				block = new Monster(x, y, maze, new StupidMonsterLogic());
				block.setColor(Color.valueOf("ff0060"));
				break;
			case AIR:
				block = new Block(x, y, maze, BlockType.AIR);
				break;
			case WALL:
				block = new Block(x, y, maze, BlockType.WALL);
				break;
		}
		
		return block;
	}
}
