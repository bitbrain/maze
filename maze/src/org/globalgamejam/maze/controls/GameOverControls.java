package org.globalgamejam.maze.controls;

import org.globalgamejam.maze.MazeGame;
import org.globalgamejam.maze.screens.MainMenuScreen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameOverControls extends Stage {

	private MazeGame game;
	
	private boolean upped;
	
	public GameOverControls(MazeGame game, int width, int height) {
		super(width, height, true);
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		super.touchDown(screenX, screenY, pointer, button);
		
		if (upped) {
			game.setScreen(new MainMenuScreen(game));
		}
		
		return true;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keyCode) {
		super.keyUp(keyCode);
		upped = true;
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
				game.setScreen(new MainMenuScreen(game));
				break;
		}
		
		return false;
	}
	
	
}
