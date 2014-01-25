package org.globalgamejam.maze;

import org.globalgamejam.maze.Block.BlockType;
import org.globalgamejam.maze.Monster.MonsterColor;
import org.globalgamejam.maze.graphics.ParticleManager;
import org.globalgamejam.maze.util.MatrixList;
import org.globalgamejam.maze.util.Updateable;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Maze implements MonsterListener {
	
	private Sprite sprite;
	
	private String[] data;
	
	private int width, height;
	
	private MatrixList<Block> blocks;
	
	private MatrixList<MonsterColor> colors;
	
	private float mazeX, mazeY;
	
	private int blockSize;
	
	private GameParticleHandler particleHandler;
	
	private TweenManager tweenManager;
	
	private ParticleManager particleManager;

	public Maze(String[] data) {
		this.data = data;
		blocks = new MatrixList<Block>();
		colors = new MatrixList<MonsterColor>();
		tweenManager = new TweenManager();
		particleManager = new ParticleManager();
		particleHandler = new GameParticleHandler(particleManager);
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
				
				if (character != ' ' && character != '\n' && character != '\r' && character != '\f') {
					Block block = factory.create(character, x, y);
					blocks.add(block);		
					
					// Event handling
					if (block instanceof Monster) {
						((Monster)block).addListener(this);
						((Monster)block).addListener(particleHandler);					
					}
				}
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
		blocks.remove(oldX, oldY);
		blocks.add(block);
	}
	
	public void removeBlock(Block block) {
		blocks.remove(block);
	}
	public boolean hasColor(int x, int y) {
		return colors.contains(x, y);
	}
	
	public MonsterColor getMonsterColor(int x, int y) {
		return colors.get(x, y);
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

	@Override
	public void onMove(Monster monster, int oldX, int oldY) {
		MonsterColor color = new MonsterColor(monster.getX(), monster.getY(), monster);
		monster.appendColor(color);
	}

	@Override
	public void onRemoveColor(Monster monster, MonsterColor color) {
		colors.remove(color);
	}

	@Override
	public void onCreateColor(Monster monster, MonsterColor color) {
		
		if (colors.contains(color.getX(), color.getY())) {
			MonsterColor otherColor = colors.get(color.getX(), color.getY());
			Monster otherMonster = otherColor.getMonster();
			otherMonster.removeColor(otherColor);
		}
		
		colors.add(color);
	}
}