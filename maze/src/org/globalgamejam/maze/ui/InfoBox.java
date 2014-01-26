package org.globalgamejam.maze.ui;

import org.globalgamejam.maze.Assets;
import org.globalgamejam.maze.Maze;
import org.globalgamejam.maze.tweens.ActorTween;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class InfoBox extends Actor {
	
	private String text;
	
	private Sprite background;
	
	private Label label;

	public InfoBox(String text, TweenManager tweenManager, final Maze maze) {
		
		setColor(1f, 1f, 1f, 0f);
		
		
		Pixmap map = new Pixmap(10, 10, Format.RGBA8888);
		
		map.setColor(0f, 0f, 0f, 0.6f);
		map.fill();
		Texture texture = new Texture(map);
		map.dispose();
		
		background = new Sprite(texture);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.FONT;
		labelStyle.fontColor = Color.WHITE;
		
		label = new Label(text, labelStyle);
		
		Tween.to(this, ActorTween.ALPHA, 2.3f)
			 .target(1f)
			 .ease(TweenEquations.easeInOutCubic)
			 .repeatYoyo(1, 0f)
			 .setCallbackTriggers(TweenCallback.COMPLETE)
			 .setCallback(new TweenCallback() {

				@Override
				public void onEvent(int type, BaseTween<?> source) {
					maze.setPaused(false);
				}
				 
			 })
			 .start(tweenManager);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		background.setColor(getColor());
		background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);
		background.setPosition(Gdx.graphics.getWidth() / 2 - background.getWidth() / 2, Gdx.graphics.getHeight() / 2 - background.getHeight() / 2);
		background.draw(batch);
		
		while (label.getPrefWidth() >= Gdx.graphics.getWidth() - 100) {
			label.setFontScale(label.getFontScaleX() * 0.9f);
		}
		
		label.setColor(getColor());
		label.setPosition(Gdx.graphics.getWidth() / 2 - label.getPrefWidth() / 2, Gdx.graphics.getHeight() / 2 - label.getPrefHeight() / 2);
		label.draw(batch, parentAlpha);
	}
	
	
}
