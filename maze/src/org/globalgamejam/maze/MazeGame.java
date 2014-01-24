package org.globalgamejam.maze;

import org.globalgamejam.maze.screens.IngameScreen;

import com.badlogic.gdx.Game;

public class MazeGame extends Game {

	@Override
	public void create() {
		setScreen(new IngameScreen(this));
	}
}
