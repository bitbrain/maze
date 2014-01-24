package org.globalgamejam.maze;

import org.globalgamejam.maze.util.Drawable;
import org.globalgamejam.maze.util.Indexable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Block implements Indexable, Drawable {
	
	private BlockType type;
	
	private Color color;
	
	private int x, y;
	
	private Maze maze;
	
	public Block(int x, int y, Maze maze, BlockType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.maze = maze;
		color = new Color(1f, 1f, 1f, 1f);
	}
	
	public void setColor(Color color) {
		this.color = new Color(color);
	}
	
	public Color getColor() {
		return color;
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
	
	public String getTextureID() {
		return null;
	}
	
	public static enum BlockType {
		
		WALL,
		AIR,
		MONSTER,
		ITEM
	}
	
	public Maze getMaze() {
		return maze;
	}

	@Override
	public void draw(Batch batch) {
		
		if (getTextureID() != null) {
			Assets manager = Assets.getInstance();
			Texture texture = manager.get(getTextureID(), Texture.class);
			batch.setColor(color);
			int size = maze.getBlockSize();
			batch.draw(texture, x * size + maze.getX(), y * size + maze.getY(), size, size);
		}
	}
}
