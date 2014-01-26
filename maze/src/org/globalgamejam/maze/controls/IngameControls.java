package org.globalgamejam.maze.controls;

import org.globalgamejam.maze.Assets;
import org.globalgamejam.maze.Maze;
import org.globalgamejam.maze.MazeGame;
import org.globalgamejam.maze.screens.GameOverScreen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class IngameControls extends Stage {

	private MazeGame game;
	
	private Maze maze;
	
	private int level;
	
	public IngameControls(int level, MazeGame game, Maze maze, int width, int height) {
		super(width, height, false);
		this.game = game;
		this.maze = maze;
		this.level = level;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keyCode) {
		super.keyDown(keyCode);
		
		switch (keyCode) {
			case Keys.BACK: case Keys.ESCAPE:
				game.setScreen(new GameOverScreen(level, game, maze.getDungeonKeeper(), true));
				break;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//removeTrail(screenX, screenY);
		return super.touchDown(screenX, screenY, pointer, button);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		removeTrail(screenX, screenY);
		return super.touchDragged(screenX, screenY, pointer);
	}
	
	private void removeTrail(int x, int y) {
		
		if (!maze.isPaused() && maze.getDungeonKeeper().canSmatch()) {
			
			Sound sound = Assets.getInstance().get(Assets.WIPE,Sound.class);
			sound.play(1f, (float) (Math.random()*0.3f+0.7f), 1f);
			
			maze.getDungeonKeeper().useSmatch();
			
			x -= maze.getX();
			y -= maze.getY();
			
			x /= maze.getBlockSize();
			y /= maze.getBlockSize();
			
			if (x >= maze.getWidth() / maze.getBlockSize()) {
				x = maze.getWidth() / maze.getBlockSize() - 1;
			}
			
			if (y >= maze.getHeight() / maze.getBlockSize()) {
				y = maze.getHeight() / maze.getBlockSize() - 1;
			}
			
			maze.removeColorTrail(x, y);
		}
	}
	
	
	
	
}
