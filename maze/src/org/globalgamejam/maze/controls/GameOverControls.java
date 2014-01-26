package org.globalgamejam.maze.controls;

import org.globalgamejam.maze.Levels;
import org.globalgamejam.maze.MazeGame;
import org.globalgamejam.maze.screens.IngameScreen;
import org.globalgamejam.maze.screens.MainMenuScreen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameOverControls extends Stage {

	private MazeGame game;
	
	private String lastLevel;
	
	public GameOverControls(String lastLevel, MazeGame game, int width, int height) {
		super(width, height, true);
		this.game = game;
		this.lastLevel = lastLevel;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		super.touchDown(screenX, screenY, pointer, button);
		
		game.setScreen(new MainMenuScreen(game, generateNext()));		
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keyCode) {
		super.keyDown(keyCode);
		
		switch (keyCode) {
			case Keys.BACK: case Keys.ESCAPE:
				game.setScreen(new MainMenuScreen(game, generateNext()));
				break;
		}
		
		return false;
	}
	
	private String generateNext() {
		return Levels.levels.get(IngameScreen.levelCount + 1);	
	}
	
	
}
