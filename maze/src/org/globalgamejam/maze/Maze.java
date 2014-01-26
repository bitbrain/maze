package org.globalgamejam.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.globalgamejam.maze.Block.BlockType;
import org.globalgamejam.maze.Monster.MonsterColor;
import org.globalgamejam.maze.graphics.ParticleManager;
import org.globalgamejam.maze.util.MatrixList;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

	private List<Monster> monsters;
	
	private Map<Color, Integer> count;

	private float mazeX, mazeY;

	private int blockSize;

	private GameParticleHandler particleHandler;

	private TweenManager tweenManager;

	private ParticleManager particleManager;
	
	private List<MazeListener> listeners;

	public Maze(String[] data) {
		this.data = data;
		blocks = new MatrixList<Block>();
		colors = new MatrixList<MonsterColor>();
		tweenManager = new TweenManager();
		particleManager = new ParticleManager();
		particleHandler = new GameParticleHandler(particleManager);
		monsters = new CopyOnWriteArrayList<Monster>();
		count = new HashMap<Color, Integer>();
		listeners = new ArrayList<MazeListener>();
		addListener(particleHandler);
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
				map.drawPixmap(floorMap, 0, 0, wall.getWidth(),
						wall.getHeight(), x * blockSize, y * blockSize,
						blockSize, blockSize);
				if (character == '1') {
					map.setColor((float)(Math.random() * 0.5f + 0.5f), (float)(Math.random() * 0.5f + 0.5f), (float)(Math.random() * 0.5f + 0.5f), 1f);
					map.drawPixmap(wallMap, 0, 0, wall.getWidth(),
							wall.getHeight(), x * blockSize, y * blockSize,
							blockSize, blockSize);
				}

				if (character != ' ' && character != '\n' && character != '\r'
						&& character != '\f') {
					Block block = factory.create(character, x, y);
					blocks.add(block);

					// Event handling
					if (block instanceof Monster) {
						Monster m = (Monster)block;
						m.addListener(this);
						m.addListener(particleHandler);
						monsters.add(m);
						
						if (!count.containsKey(m.getColor())) {
							count.put(m.getColor(), 1);
						} else {
							count.put(m.getColor(), count.get(m.getColor()) + 1);
						}
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
		
		if (block instanceof Monster) {
			Monster m = (Monster)block;
			monsters.remove(m);
			
			if (count.get(m.getColor()) != null) {
				int newCount = count.get(m.getColor()) - 1;
				
				if (newCount < 1) {
					count.remove(m.getColor());
				} else {
					count.put(m.getColor(), newCount);
				}
			}
		}
	}

	public boolean gameover() {
		return count.size() <= 1;
	}

	public boolean hasColor(int x, int y) {
		return colors.contains(x, y);
	}

	public MonsterColor getMonsterColor(int x, int y) {
		return colors.get(x, y);
	}
	
	public void removeColorTrail(int x, int y) {
		
		MonsterColor color = colors.get(x, y);
		
		if (color != null) {
			Monster m = color.getMonster();
			m.removeColor(color);
			
			for (MazeListener l : listeners) {
				l.onRemoveTrailColor(color);
			}
			
			colors.remove(x, y);
		}
		
	}
	
	public void removeMonsterColor(MonsterColor color) {
		colors.remove(color);
	}
	
	public void addListener(MazeListener listener) {
		listeners.add(listener);
	}

	public void draw(Batch batch, float delta) {

		tweenManager.update(delta);

		if (sprite != null) {

			sprite.draw(batch);
		}

		particleManager.render(batch, delta);

		for (Monster m : monsters) {
			m.update(delta);
			m.draw(batch);
		}
		
		if (Gdx.input.isKeyPressed(Keys.F1) && MazeGame.DEBUG) {
			for (Block b : blocks) {
				Texture tex = Assets.getInstance().get(Assets.WALL, Texture.class);
				
				Color color = new Color(0.6f, 0.6f, 0.6f, 0.7f);
				
				if (b.getType() == BlockType.MONSTER) {
					Monster m = (Monster)b;
					color.r = m.getColor().r;
					color.g = m.getColor().g;
					color.b = m.getColor().b;
				} else {
					color.a = 0f;
				}
				
				batch.setColor(color);
				batch.draw(tex, b.getX() * blockSize + getX(), b.getY() * blockSize + getY(), blockSize, blockSize);
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.F2) && MazeGame.DEBUG) {
			for (MonsterColor color : colors) {
				Texture tex = Assets.getInstance().get(Assets.WALL, Texture.class);
				
				Color clr = new Color(color);
				clr.a = 0.8f;
				batch.setColor(clr);
				batch.draw(tex, color.getX() * blockSize + getX(), color.getY() * blockSize + getY(), blockSize, blockSize);
			}
		}

	}

	@Override
	public void onMove(Monster monster, int oldX, int oldY) {
		MonsterColor color = new MonsterColor(monster.getX(), monster.getY(),
				monster);
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