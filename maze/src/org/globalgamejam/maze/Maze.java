package org.globalgamejam.maze;

import org.globalgamejam.maze.Block.BlockType;
import org.globalgamejam.maze.graphics.ParticleManager;
import org.globalgamejam.maze.util.MatrixList;
import org.globalgamejam.maze.util.PositionColor;
import org.globalgamejam.maze.util.Updateable;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Maze {
	
	private MatrixList<PositionColor> positionColors;

	
	
	
	private Sprite sprite;
	
	private String[] data;
	
	private int width, height;
	
	private MatrixList<Block> blocks;
	
	private float mazeX, mazeY;
	
	private int blockSize;
	
	private TweenManager tweenManager;
	
	private ParticleManager particleManager;

	public Maze(String[] data) {
		this.data = data;
		blocks = new MatrixList<Block>();
		tweenManager = new TweenManager();
		particleManager = new ParticleManager();
		positionColors = new MatrixList<PositionColor>();
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
	
	public TweenManager getTweenManager() {
		return tweenManager;
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
		Texture wall = Assets.getInstance().get(Assets.WALL, Texture.class);
		TextureData texData = wall.getTextureData();
		texData.prepare();
		Pixmap wallMap = texData.consumePixmap();
		Texture floor = Assets.getInstance().get(Assets.FLOOR, Texture.class);
		TextureData floorData = floor.getTextureData();
		floorData.prepare();
		Pixmap floorMap = floorData.consumePixmap();
		
		for (int y = 0; y < data.length; y++) {
			
			String line = data[y];
			
			for (int x = 0; x < line.length(); ++x) {
				char character = line.charAt(x);
				map.drawPixmap(floorMap, 0, 0, wall.getWidth(), wall.getHeight(), x * blockSize, y * blockSize, blockSize, blockSize);
				if (character == '1') {
					map.setColor(Color.WHITE);
					map.drawPixmap(wallMap, 0, 0, wall.getWidth(), wall.getHeight(), x * blockSize, y * blockSize, blockSize, blockSize);
				}
				
				Block block = factory.create(character, x, y);
				blocks.add(block);				
			}
		}
		
		wallMap.dispose();
		floorMap.dispose();
		
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
		
		tweenManager.update(delta);
		
		if (sprite != null) {
			
			sprite.draw(batch);
		}

		particleManager.render(batch, delta);
		
		for (Block block : blocks) {
			if (block instanceof Updateable) {
				((Updateable)block).update(delta);
			}
			
			block.draw(batch);
		}
	}
}
