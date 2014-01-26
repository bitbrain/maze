package org.globalgamejam.maze.screens;

import org.globalgamejam.maze.Assets;
import org.globalgamejam.maze.DungeonKeeper;
import org.globalgamejam.maze.Maze;
import org.globalgamejam.maze.MazeGame;
import org.globalgamejam.maze.controls.IngameControls;
import org.globalgamejam.maze.ui.InfoBox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class IngameScreen implements Screen {
	
	private Maze maze;
	
	private Batch batch;
	
	private OrthographicCamera camera;
	
	private Stage stage;
	
	private MazeGame game;
	
	public IngameScreen(MazeGame game, String[] data) {
		this.maze = new Maze(data);
		this.game = game;
		maze.setPaused(true);
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		if (maze.gameover()) {
			game.setScreen(new GameOverScreen(game));
		}
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		maze.draw(batch, delta);
		batch.end();
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
		if (stage != null) {
			stage.setViewport(width, height, false);
		} else {
			stage = new IngameControls(game, maze, width, height);
			Gdx.input.setInputProcessor(stage);
			
			stage.addActor(new InfoBox("Defend your minions!", maze.getTweenManager(), maze));
		}
		camera.setToOrtho(true, width, height);
		maze.build(width, height);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		
		Music music = Assets.getInstance().get(Assets.DRIP, Music.class);
		music.setLooping(true);
		music.play();
		music = Assets.getInstance().get(Assets.INGAME, Music.class);
		music.setLooping(true);
		music.play();
	}

	@Override
	public void hide() {
		Assets.getInstance().get(Assets.INGAME, Music.class).stop();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
