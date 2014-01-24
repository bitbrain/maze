package org.globalgamejam.maze;

public class Block {
	
	private BlockType type;
	
	private int x, y;
	
	public Block(int x, int y, BlockType type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	

	public BlockType getType() {
		return type;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public static enum BlockType {
		
		WALL,
		AIR
	}
}
