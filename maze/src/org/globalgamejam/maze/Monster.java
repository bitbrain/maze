package org.globalgamejam.maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.globalgamejam.maze.ai.StupidMonsterLogic;
import org.globalgamejam.maze.tweens.BlockTween;
import org.globalgamejam.maze.tweens.SpriteTween;
import org.globalgamejam.maze.util.Direction;
import org.globalgamejam.maze.util.Indexable;
import org.globalgamejam.maze.util.Updateable;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Monster extends Block implements Updateable {
	
	public static final int LENGTH = 10;

	private MonsterLogic logic;
	
	private Direction direction;
	
	private Direction lastDirection;
	
	private ArrayList<MonsterListener> listeners;

	private MonsterColor firstColor;
	
	private Queue<MonsterColor> colors;
	
	public Monster(int x, int y, Maze maze, MonsterLogic logic) {
		super(x, y, maze, BlockType.MONSTER);
		this.logic = logic;
		direction = Direction.NONE;
		lastDirection = direction;
		listeners = new ArrayList<MonsterListener>();
		colors = new LinkedList<MonsterColor>();
	}
	
	@Override
	public void update(float delta) {
		logic.update(delta, this);
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public Direction getLastDirection() {
		return lastDirection;
	}
	
	public void addListener(MonsterListener l) {
		listeners.add(l);
	}
	
	public void removeListener(MonsterListener l) {
		listeners.remove(l);
	}

	/* (non-Javadoc)
	 * @see org.globalgamejam.maze.Block#setPosition(int, int)
	 */
	@Override
	public void setPosition(int x, int y) {
		
		float deltaX = x - getX();
		float deltaY = y - getY();
		
		lastDirection = direction;
		
		if (deltaX > 0) {
			direction = Direction.RIGHT;
		} else if (deltaX < 0) {
			direction = Direction.LEFT;
		}
		
		if (deltaY > 0) {
			direction = Direction.DOWN;
		} else if (deltaY < 0) {
			direction = Direction.UP;
		}
		
		int oldX = getX();
		int oldY = getY();
		
		super.setPosition(x, y);
		
		for (MonsterListener l : listeners) {
			l.onMove(this, oldX, oldY);
		}
	}
	
	public void move(Direction direction) {
		switch (direction) {
			case DOWN:
				setPosition(getX(), getY() + 1);
				break;
			case LEFT:
				setPosition(getX() - 1, getY());
				break;
			case RIGHT:
				setPosition(getX() + 1, getY());
				break;
			case UP:
				setPosition(getX(), getY() - 1);
				break;
			default:
				break;		
		}
		
		animateMovement(direction);
	}
	
	public boolean canMove(Direction direction) {
		
		if (direction.equals(Direction.getOpposite(direction))) {
			return false;
		}
		
		switch (direction) {
		case DOWN:
			return !getMaze().isBlocked(getX(), getY() + 1);
		case LEFT:
			return !getMaze().isBlocked(getX() - 1, getY());
		case RIGHT:
			return !getMaze().isBlocked(getX() + 1, getY());
		case UP:
			return !getMaze().isBlocked(getX(), getY() - 1);
		default:
			break;
		
		}
		
		return true;
	}
	
	public void appendColor(MonsterColor color) {
		
		if (!colors.isEmpty() && colors.size() >= LENGTH) {
			MonsterColor junk = colors.remove();
			for (MonsterListener l : listeners) {
				l.onRemoveColor(this, junk);
			}
		}
		
		color.setNext(firstColor);
		firstColor = color;
		colors.add(color);
		
		color.r = getColor().r;
		color.g = getColor().g;
		color.b = getColor().b;
		color.a = getColor().a;
		
		for (MonsterListener l : listeners) {
			l.onCreateColor(this, color);
		}
	}

	/* (non-Javadoc)
	 * @see org.globalgamejam.maze.Block#draw(com.badlogic.gdx.graphics.g2d.Batch)
	 */
	@Override
	public void draw(Batch batch) {
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		
		float factor = 0f;
		TweenManager tweenManager = getMaze().getTweenManager();
		
		switch (getDirection()) {
		case LEFT:
			factor = 90f;
			break;
		case NONE:
			break;
		case RIGHT:
			factor = -90f;
			break;
		case UP:
			factor = 180f;
			break;
		default:
			break;
		
		}
		
		Tween.to(sprite, SpriteTween.ROTATION, 0.105f)
		     .target(factor)
		     .ease(TweenEquations.easeInBounce)
		     .start(tweenManager);
		
		super.draw(batch);
	}

	/* (non-Javadoc)
	 * @see org.globalgamejam.maze.Block#getTextureID()
	 */
	@Override
	public String getTextureID() {
		return Assets.MONSTER;
	}
	

	
	private void animateMovement(Direction direction) {
		
		TweenManager manager = getMaze().getTweenManager();
		int blockSize = getMaze().getBlockSize();
		
		manager.killTarget(this);
		
		int tweenType = BlockTween.OFFSET_Y;
		
		if (direction == Direction.LEFT || direction == Direction.RIGHT) {
			tweenType = BlockTween.OFFSET_X;
		}
		
		switch (direction) {
			case DOWN:
				setOffsetY(-blockSize);
				break;
			case LEFT:
				setOffsetX(blockSize);
				break;
			case RIGHT:
				setOffsetX(-blockSize);
				break;
			case UP:
				setOffsetY(blockSize);
				break;
			default:
				break;		
		}
		
		Tween.to(this, tweenType, StupidMonsterLogic.INTERVAL / 1000f)
		     .target(0f)
		     .ease(TweenEquations.easeInOutCubic)
		     .start(manager);
	}
	
	public static class MonsterColor extends Color implements Indexable {
		
		private int x, y;
		
		private MonsterColor next;
		
		public MonsterColor(int x, int y) {
			this.x = x;
			this.y = y;
			this.next = null;
		}

		@Override
		public int getX() {
			return x;
		}

		@Override
		public int getY() {
			return y;
		}
		
		public void setX(int x) {
			this.x = x;
		}
		
		public void setY(int y) {
			this.y = y;
		}
		
		public MonsterColor getNext() {
			return next;
		}
		
		public void setNext(MonsterColor next) {
			this.next = next;
		}
		
	}

}
