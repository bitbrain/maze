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
	
	public static final int LENGTH = 7;

	private MonsterLogic logic;
	
	private Direction direction;
	
	private Direction lastDirection;
	
	private ArrayList<MonsterListener> listeners;

	private MonsterColor firstColor;
	
	private Queue<MonsterColor> colors;
	
	private boolean angry;
	
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

		lastDirection = direction;
		
		direction = Direction.translate(this, x, y);
		int oldX = getX();
		int oldY = getY();
		
		super.setPosition(x, y);
		
		for (MonsterListener l : listeners) {
			l.onMove(this, oldX, oldY);
		}
	}
	
	public boolean isColorOf(MonsterColor color) {
		return getColor().r == color.r &&
				getColor().g == color.g &&
				getColor().b == color.b;
	}
	
	public void setDirection(Direction direction) {
		lastDirection = this.direction;
		this.direction = direction;
	}
	
	public void kill() {
		getMaze().removeBlock(this);
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
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Monster [direction=" + direction + ", firstColor=" + firstColor
				+ "]";
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
	
	public void setAngry(boolean angry) {
		if (this.angry != angry) {
			
			if (angry) {
				Tween.to(this, BlockTween.SCALE, 0.2f)
					 .target(1.2f)
					 .ease(TweenEquations.easeInOutBounce)
					 .repeatYoyo(Tween.INFINITY, 0f)
					 .start(getMaze().getTweenManager());
			} else {
				getMaze().getTweenManager().killTarget(this, BlockTween.SCALE);
			}
			
			this.angry = angry;
		}
	}
	
	public void appendColor(MonsterColor color) {
		
		if (!colors.isEmpty() && colors.size() >= LENGTH) {
			MonsterColor junk = colors.remove();
			for (MonsterListener l : listeners) {
				l.onRemoveColor(this, junk);
			}
		}
		
		color.setNext(firstColor);
		if (firstColor != null) {
			firstColor.setPrevious(color);
		}
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
	
	public void removeColor(MonsterColor color) {
		for (MonsterListener l : listeners) {
			l.onRemoveColor(this, color);
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
		
		manager.killTarget(this, BlockTween.OFFSET_X);
		manager.killTarget(this, BlockTween.OFFSET_Y);
		
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
		     .ease(TweenEquations.easeNone)
		     .start(manager);
	}
	
	public static class MonsterColor extends Color implements Indexable {

		/* (non-Javadoc)
		 * @see java.lang.Object */
		private int x, y;
		
		private MonsterColor next, previous;
		
		private Monster monster;
		
		public MonsterColor(int x, int y, Monster monster) {
			this.x = x;
			this.y = y;
			this.next = null;
			this.monster = monster;
		}
		
		public Monster getMonster() {
			return monster;
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
		
		public MonsterColor getPrevious() {
			return previous;
		}
		
		public void setNext(MonsterColor next) {
			this.next = next;
		}
		
		public void setPrevious(MonsterColor previous) {
			this.previous = previous;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((next == null) ? 0 : next.hashCode());
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			MonsterColor other = (MonsterColor) obj;
			if (next == null) {
				if (other.next != null)
					return false;
			} else if (!next.equals(other.next))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

	}

}