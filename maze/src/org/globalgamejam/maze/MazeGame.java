package org.globalgamejam.maze;

import org.globalgamejam.maze.screens.IngameScreen;

import com.badlogic.gdx.Game;

public class MazeGame extends Game {
	
	public static final String[] data = new String[]{
		"01001100",
		"01000000",
		"00000100",
		"00000100"
	};

	@Override
	public void create() {			
		setScreen(new IngameScreen(this, data));
	}
}
