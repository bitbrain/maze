package org.globalgamejam.maze;

import org.globalgamejam.maze.util.Drawable;
import org.globalgamejam.maze.util.Indexable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Block implements Indexable, Drawable {
	
	private BlockType type;
	
	private Color color;
	
	private int x, y;
	
	private Maze maze;
	
	protected Sprite sprite;
	
	private float offsetX, offsetY;
	
	public Block(int x, int y, Maze maze, BlockType type) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.maze = maze;
		color = new Color(1f, 1f, 1f, 1f);
		
		if (getTextureID() != null) {
			Assets manager = Assets.getInstance();
			Texture texture = manager.get(getTextureID(), Texture.class);
			sprite = new Sprite(texture);
			sprite.flip(false, true);
		}
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
	
	public float getOffsetX() {
		return offsetX;
	}
	
	public float getOffsetY() {
		return offsetY;
	}
	
	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}
	
	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
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
	
	public Maze getMaze() {
		return maze;
	}
	
	public void setPosition(int x, int y) {
		
		int oldX = x;
		int oldY = y;
		this.x = x;
		this.y = y;

		maze.moveBlock(this, oldX, oldY);
	}

	@Override
	public void draw(Batch batch) {
		
		if (sprite != null) {
			int size = maze.getBlockSize();
			sprite.setBounds(x * size + maze.getX() + offsetX, y * size + maze.getY() + offsetY, size, size);
			sprite.setColor(color);
			sprite.draw(batch);
		}
	}
	
	public static enum BlockType {
		
		WALL,
		AIR,
		MONSTER,
		ITEM
	}
}
