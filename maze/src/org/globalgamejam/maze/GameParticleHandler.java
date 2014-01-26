package org.globalgamejam.maze;

import java.util.HashMap;
import java.util.Map;

import org.globalgamejam.maze.Monster.MonsterColor;
import org.globalgamejam.maze.ai.StupidMonsterLogic;
import org.globalgamejam.maze.graphics.ParticleManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;

public class GameParticleHandler implements MonsterListener, MazeListener {
	
	private ParticleManager manager;
	
	private Map<MonsterColor, ParticleEffect> effects;
	
	public GameParticleHandler(ParticleManager manager) {
		this.manager = manager;
		effects = new HashMap<MonsterColor, ParticleEffect>();
	}

	@Override
	public void onMove(Monster monster, int oldX, int oldY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemoveColor(Monster monster, MonsterColor color) {
		
		ParticleEffect effect = effects.get(color);
		
		if (effect != null) {
			effect.setDuration(0);
		}
		
	}

	@Override
	public void onCreateColor(Monster monster, MonsterColor color) {
		ParticleEffect effect = manager.create(Assets.getInstance().get(Assets.FLARE, ParticleEffect.class), false);
		Maze maze = monster.getMaze();
		
		float x = color.getX() * maze.getBlockSize() + maze.getX() + maze.getBlockSize() / 2;
		float y = color.getY() * maze.getBlockSize() + maze.getY() + maze.getBlockSize() / 2;
		
		effect.setPosition(x, y);

		manager.setColor(effect, new float[]{color.r, color.g, color.b}, new float[]{0f});
		effect.start();
		
		for (ParticleEmitter e : effect.getEmitters()) {
			e.getScale().setLow(Gdx.graphics.getWidth() / 100f);
			e.getDuration().setLow(Monster.LENGTH * StupidMonsterLogic.INTERVAL);
			e.getLife().setLow(Monster.LENGTH * StupidMonsterLogic.INTERVAL);
			e.getDuration().setLowMin(Monster.LENGTH * StupidMonsterLogic.INTERVAL);
			e.getLife().setLowMin(Monster.LENGTH * StupidMonsterLogic.INTERVAL);
			e.getVelocity().setLow(Gdx.graphics.getWidth() / 300f);
		}
		
		effects.put(color, effect);
	}

	@Override
	public void onRemoveTrailColor(MonsterColor color) {
		Maze maze = color.getMonster().getMaze();
		ParticleEffect effect = manager.create(Assets.getInstance().get(Assets.FLARE, ParticleEffect.class), false);
		float x = color.getX() * maze.getBlockSize() + maze.getX() + maze.getBlockSize() / 2;
		float y = color.getY() * maze.getBlockSize() + maze.getY() + maze.getBlockSize() / 2;
		
		effect.setPosition(x, y);

		manager.setColor(effect, new float[]{color.r, color.g, color.b}, new float[]{0f});
		effect.start();
		
		for (ParticleEmitter e : effect.getEmitters()) {
			e.getScale().setLow(Gdx.graphics.getWidth() / 30f);
			e.getDuration().setLow(Monster.LENGTH * StupidMonsterLogic.INTERVAL / 104);
			e.getLife().setLow(Monster.LENGTH * StupidMonsterLogic.INTERVAL / 140);
			e.getDuration().setLowMin(Monster.LENGTH * StupidMonsterLogic.INTERVAL / 140);
			e.getLife().setLowMin(Monster.LENGTH * StupidMonsterLogic.INTERVAL / 140);
			e.getVelocity().setLow(Gdx.graphics.getWidth() / 50f);
		}
		
		effects.put(color, effect);
		
		Gdx.input.vibrate(20);
	}

}
