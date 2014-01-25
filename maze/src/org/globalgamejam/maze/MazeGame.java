package org.globalgamejam.maze;

import java.io.IOException;

import org.globalgamejam.maze.io.MazeFileReader;
import org.globalgamejam.maze.screens.IngameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MazeGame extends Game {

	@Override
	public void create() {		
		
		Assets manager = Assets.getInstance();
		manager.loadAll();
		
		// Load sample level
		MazeFileReader reader = new MazeFileReader();
		
		String[] data;
		try {
			data = reader.read("test.mz");
			setScreen(new IngameScreen(this, data));
		} catch (IOException e) {
			e.printStackTrace();
			Gdx.app.exit();
		}		
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
