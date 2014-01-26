package org.globalgamejam.maze;

import org.globalgamejam.maze.screens.MainMenuScreen;
import org.globalgamejam.maze.tweens.ActorTween;
import org.globalgamejam.maze.tweens.BlockTween;
import org.globalgamejam.maze.tweens.SpriteTween;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MazeGame extends Game {
	
	public static final boolean DEBUG = true;

	@Override
	public void create() {		
		
		Assets manager = Assets.getInstance();
		manager.loadAll();
			
		// Load tweens
		Tween.registerAccessor(Block.class, new BlockTween());			
		Tween.registerAccessor(Sprite.class, new SpriteTween());		
		Tween.registerAccessor(Actor.class, new ActorTween());	
		
		setScreen(new MainMenuScreen(this, "maze1.mz"));
	
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
