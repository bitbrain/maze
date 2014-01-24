package org.globalgamejam.maze;

import org.globalgamejam.maze.util.MatrixList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Maze {
	
	private Texture texture;
	
	private String[] data;

	public Maze(String[] data) {
		this.data = data;
	}
	
	public void build(int width, int height) {
		
		if (texture != null) {
			texture.dispose();
		}
		int blockSize = 0;
		
		if (width < height) {
			blockSize = width / data[0].length();
		} else {
			blockSize = height / data.length;
		}
		
		int mazeWidth = blockSize * data[0].length();
		int mazeHeight = Gdx.graphics.getHeight() * data.length;
		
		Pixmap map = new Pixmap(mazeWidth, mazeHeight, Format.RGBA8888);
		
		texture = new Texture(map);
		map.dispose();
	}
	
	public void draw(Batch batch, float delta) {
		if (texture != null) {
			batch.draw(texture, 0, 0, texture.getWidth(), texture.getHeight());
		}
	}
}
