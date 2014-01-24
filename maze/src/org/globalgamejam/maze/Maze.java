package org.globalgamejam.maze;

import com.badlogic.gdx.Gdx;
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

	public Maze(String[] data) {
		this.data = data;
	}
	
	public Block getBlock(int x, int y) {
		// TODO
		return null;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void build(int width, int height) {
		
		int blockSize = 0;
		
		if (width < height) {
			blockSize = width / data[0].length();
		} else {
			blockSize = height / data.length;
		}
		
		int mazeWidth = blockSize * data[0].length();
		int mazeHeight = blockSize * data.length;
		
		System.out.println(mazeWidth + ", " + mazeHeight);
		
		Pixmap map = new Pixmap(mazeWidth, mazeHeight, Format.RGBA8888);
		
		for (int y = 0; y < data.length; y++) {
			
			String line = data[y];
			
			for (int x = 0; x < line.length(); ++x) {
				char c = line.charAt(x);
				
				if (c == '0') {
					map.setColor(Color.CYAN);					
				} else if (c == '1') {
					map.setColor(Color.GRAY);
				}
				
				map.fillRectangle(x * blockSize, y * blockSize, blockSize, blockSize);
			}
		}
		
		sprite = new Sprite(new Texture(map));
		sprite.flip(false, true);
		map.dispose();
	}
	
	public void draw(Batch batch, float delta) {
		if (sprite != null) {
			
			sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);
			
			sprite.draw(batch);
		}
	}
}
