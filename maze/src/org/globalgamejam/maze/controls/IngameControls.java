package org.globalgamejam.maze.controls;

import org.globalgamejam.maze.Maze;
import org.globalgamejam.maze.MazeGame;
import org.globalgamejam.maze.screens.GameOverScreen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class IngameControls extends Stage {

	private MazeGame game;
	
	private Maze maze;
	
	public IngameControls(MazeGame game, Maze maze, int width, int height) {
		super(width, height, false);
		this.game = game;
		this.maze = maze;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keyCode) {
		super.keyDown(keyCode);
		
		switch (keyCode) {
			case Keys.BACK: case Keys.ESCAPE:
				game.setScreen(new GameOverScreen(game));
				break;
		}
		
		return false;
	}
	
	
	
	
}
