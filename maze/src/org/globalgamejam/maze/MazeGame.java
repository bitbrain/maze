package org.globalgamejam.maze;

import org.globalgamejam.maze.screens.IngameScreen;

import com.badlogic.gdx.Game;

public class MazeGame extends Game {
	
	public static final String[] data = new String[]{
		"111111111111",
		"1r01000010b1",
		"100100100011",
		"100000101011",
		"100000101011",
		"100000101011",
		"1g01001010y1",
		"111111111111"
	};

	@Override
	public void create() {		
		
		Assets manager = Assets.getInstance();
		manager.loadAll();
		
		setScreen(new IngameScreen(this, data));
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Game#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		
		Assets manager = Assets.getInstance();
		manager.dispose();
	}
	
	
}
