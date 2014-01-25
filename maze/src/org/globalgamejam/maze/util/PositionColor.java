package org.globalgamejam.maze.util;

import com.badlogic.gdx.graphics.Color;


public class PositionColor extends Color implements Indexable {
	
	private int x;
	private int y;
	
	
	
	public PositionColor(float r, float g, float b, int x, int y) {
		super(r, g, b, 1f);
		this.x = x;
		this.y = y;
		
		
		
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
