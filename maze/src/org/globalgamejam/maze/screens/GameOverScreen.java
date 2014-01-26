package org.globalgamejam.maze.screens;

import org.globalgamejam.maze.Assets;
import org.globalgamejam.maze.DungeonKeeper;
import org.globalgamejam.maze.MazeGame;
import org.globalgamejam.maze.controls.GameOverControls;
import org.globalgamejam.maze.tweens.ActorTween;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class GameOverScreen implements Screen {
	
	private MazeGame game;
	
	private Stage stage;
	
	private Sprite background;
	
	private Batch batch;
	
	private TweenManager tweenManager;
	
	private DungeonKeeper player;
	
	public GameOverScreen(MazeGame game, DungeonKeeper player) {
		this.game = game;
		this.player = player;
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		stage.act(delta);
		batch.begin();
		background.draw(batch);
		batch.end();
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		if (stage != null) {
			stage.setViewport(width, height);
		} else {
			stage = new GameOverControls(game, width, height);
			Gdx.input.setInputProcessor(stage);
			Gdx.input.setCatchBackKey(true);
			
			
			final Image image = new Image(Assets.getInstance().get(Assets.LOGO, Texture.class));
			stage.addActor(image);
			
			image.setScale(Gdx.graphics.getWidth() / 1000f);
			image.setColor(1f, 1f, 1f, 0f);
			image.setX(Gdx.graphics.getWidth() / 2 - (image.getWidth() * image.getScaleX()) / 2);
			image.setY(Gdx.graphics.getHeight());
			background.setBounds(0, 0, width, height);
			
			LabelStyle startStyle = new LabelStyle();
			startStyle.font = Assets.FONT;
			startStyle.fontColor = new Color(1f, 1f, 1f, 1f);
			
			Label start = new Label("Game Over!", startStyle);
			start.setFontScale(Gdx.graphics.getHeight() / 300f);
			start.setPosition(Gdx.graphics.getWidth() / 2 - start.getPrefWidth() / 2, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4.5f);
			stage.addActor(start);
			
			Tween.to(start, ActorTween.ALPHA, 0.7f)
				 .target(0.0f)
				 .ease(TweenEquations.easeInOutCubic)
				 .repeatYoyo(Tween.INFINITY, 0f)
				 .start(tweenManager);
			
			Label score = new Label(player.getPoints() + " Points", startStyle);
			score.setFontScale(Gdx.graphics.getHeight() / 500f);
			score.setColor(Color.valueOf("330077"));
			score.setPosition(Gdx.graphics.getWidth() / 2 - score.getPrefWidth() / 2, Gdx.graphics.getHeight() / 4.5f);
			stage.addActor(score);
			
			image.setY(Gdx.graphics.getHeight() - image.getHeight() - 10f);

			Tween.to(image, ActorTween.POPUP, 2f)
			.target(Gdx.graphics.getHeight() - image.getHeight())
			.ease(TweenEquations.easeInOutQuad)
			.repeatYoyo(Tween.INFINITY, 0f)
			.start(tweenManager);
		}
			
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		background = new Sprite(Assets.getInstance().get(Assets.BACKGROUND, Texture.class));
		tweenManager = new TweenManager();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
