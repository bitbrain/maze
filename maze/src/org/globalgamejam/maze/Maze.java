package org.globalgamejam.maze;

import org.globalgamejam.maze.Block.BlockType;
import org.globalgamejam.maze.util.MatrixList;
import org.globalgamejam.maze.util.Updateable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Maze {
	
	private Sprite sprite;
	
	private String[] data;
	
	private int width, height;
	
	private MatrixList<Block> blocks;
	
	private float mazeX, mazeY;
	
	private int blockSize;

	public Maze(String[] data) {
		this.data = data;
		blocks = new MatrixList<Block>();
	}
	
	public Block getBlock(int x, int y) {
		return blocks.get(x, y);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float getX() {
		return mazeX;
	}
	
	public float getY() {
		return mazeY;
	}
	
	public int getBlockSize() {
		return blockSize;
	}
	
	public boolean isBlocked(int x, int y) {
		Block b = blocks.get(x, y);
		
		if (b != null) {
			return b.getType().equals(BlockType.WALL);
		} else {
			return true;
		}
	}
	
	public void build(int width, int height) {
		
		BlockFactory factory = new BlockFactory(this);
		
		if (width > height) {
			blockSize = width / data[0].length();
		} else {
			blockSize = height / data.length;
		}
		
		int mazeWidth = blockSize * data[0].length();
		int mazeHeight = blockSize * data.length;
		
		mazeX = width / 2 - mazeWidth / 2;
		mazeY = height / 2 - mazeHeight / 2;
		
		Pixmap map = new Pixmap(mazeWidth, mazeHeight, Format.RGBA8888);
		
		for (int y = 0; y < data.length; y++) {
			
			String line = data[y];
			
			for (int x = 0; x < line.length(); ++x) {
				char character = line.charAt(x);
									
				if (character == '1') {
					map.setColor(Color.GRAY);
				} else {
					map.setColor(Color.BLACK);
				}
				
				map.fillRectangle(x * blockSize, y * blockSize, blockSize, blockSize);
				
				Block block = factory.create(character, x, y);
				blocks.add(block);				
			}
		}
		
		sprite = new Sprite(new Texture(map));
		sprite.flip(false, true);
		sprite.setPosition(mazeX, mazeY);
		map.dispose();
		
		this.width = mazeWidth;
		this.height = mazeHeight;
	}
	
	void moveBlock(Block block, int oldX, int oldY) {
		Block air = new Block(oldX, oldY, this, BlockType.AIR);
		blocks.add(air);
		blocks.add(block);
	}
	
	public void draw(Batch batch, float delta) {
		if (sprite != null) {
			
			sprite.draw(batch);
		}

		
//		for (int y = 0; y < getHeight() / getBlockSize(); ++y) {
//			for (int x = 0; x < getWidth() / getBlockSize(); ++x) {
//				if (isBlocked(x, y)) {
//					batch.setColor(Color.PINK);
//					Texture texture = Assets.getInstance().get("monster.png", Texture.class);
//					batch.draw(texture, x * getBlockSize() + getX(), y * getBlockSize() + getY(), getBlockSize(), getBlockSize());
//				}
//			}
//		}
		
		for (Block block : blocks) {
			if (block instanceof Updateable) {
				((Updateable)block).update(delta);
			}
			
			block.draw(batch);
		}
	}
}
