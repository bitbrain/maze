package org.globalgamejam.maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.globalgamejam.maze.ai.StupidMonsterLogic;
import org.globalgamejam.maze.tweens.BlockTween;
import org.globalgamejam.maze.tweens.SpriteTween;
import org.globalgamejam.maze.util.Direction;
import org.globalgamejam.maze.util.Indexable;
import org.globalgamejam.maze.util.SoundUtils;
import org.globalgamejam.maze.util.Updateable;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.audio.Sound;
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

	private boolean dead;
	
	private long moveInterval;

	public Monster(int x, int y, Maze maze, MonsterLogic logic) {
		super(x, y, maze, BlockType.MONSTER);
		this.logic = logic;
		direction = Direction.NONE;
		lastDirection = direction;
		listeners = new ArrayList<MonsterListener>();
		colors = new LinkedList<MonsterColor>();
		moveInterval = StupidMonsterLogic.INTERVAL;
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
	
	public long getInterval() {
		return moveInterval;
	}
	
	public void setInterval(long interval) {
		this.moveInterval = interval;
	}
	

	public void addListener(MonsterListener l) {
		listeners.add(l);
	}

	public void removeListener(MonsterListener l) {
		listeners.remove(l);
	}

	/*
	 * (non-Javadoc)
	 * 
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
		return getColor().r == color.r && getColor().g == color.g
				&& getColor().b == color.b;
	}

	public void setDirection(Direction direction) {
		lastDirection = this.direction;
		this.direction = direction;
	}

	public void kill() {
		if (!dead) {
			getMaze().removeBlock(this);
			dead = true;
			
			for (MonsterColor c : colors) {
				getMaze().removeMonsterColor(c);
			}
			
			colors.clear();
			Assets.getInstance().get(Assets.SPLASH, Sound.class).play(0.5f, (float) (Math.random() * 0.5 + 0.5f), 1f);
			if (Math.random() < 0.5f) {
				Assets.getInstance().get(Assets.KILL1, Sound.class).play(0.5f, (float) (Math.random() * 0.5 + 0.5f), 1f);
			} else {
				Assets.getInstance().get(Assets.KILL2, Sound.class).play(0.5f, (float) (Math.random() * 0.5 + 0.5f), 1f);
			}
		}
	}
	
	public boolean isDead() {
		return dead;
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
		
		if (Math.random() < 0.5f) {
			
			if (isAngry()) {
				Assets.getInstance().get(Assets.RUNFASTER, Sound.class).play(0.5f, (float) (Math.random() * 0.5 + 0.5f), 1f);
			} else {
				Assets.getInstance().get(Assets.RUN, Sound.class).play(0.1f, (float) (Math.random() * 0.5 + 0.5f), 1f);
			}
			
		}
		
		if (Math.random() < 0.01f) {
			SoundUtils.playRandomSound("voicec", 5);
		}

		animateMovement(direction);
	}

	/*
	 * (non-Javadoc)
	 * 
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
		
		int x = -1, y = -1;

		switch (direction) {
		case DOWN:
			x = getX();
			y = getY() + 1;
			break;
			//return !getMaze().isBlocked(getX(), getY() + 1);
		case LEFT:
			x = getX() - 1;
			y = getY();
			break;
			//return !getMaze().isBlocked(getX() - 1, getY());
		case RIGHT:
			x = getX() + 1;
			y = getY();
			break;
			//return !getMaze().isBlocked(getX() + 1, getY());
		case UP:
			x = getX();
			y = getY() - 1;
			break;
			//return !getMaze().isBlocked(getX(), getY() - 1);
		default:
			break;
		}
		
		Block block = getMaze().getBlock(x, y);
		
		if (block != null && !isAngry() && block.getType() == BlockType.MONSTER) {
			return false;
		} 
		
		return !getMaze().isBlocked(x, y);
	}

	public void setAngry(boolean angry) {
		if (this.angry != angry) {

			if (angry) {
				SoundUtils.playRandomSound("aggro", 15);
				Tween.to(this, BlockTween.SCALE, 0.2f).target(1.2f)
						.ease(TweenEquations.easeInOutBounce)
						.repeatYoyo(Tween.INFINITY, 0f)
						.start(getMaze().getTweenManager());
			} else {
				getMaze().getTweenManager().killTarget(this, BlockTween.SCALE);
				Tween.to(this, BlockTween.SCALE, 0.2f).target(1f)
				.ease(TweenEquations.easeInOutBounce)
				.start(getMaze().getTweenManager());
			}

			this.angry = angry;
		}
	}
	
	public boolean isAngry() {
		return angry;
	}

	public void appendColor(MonsterColor color) {

		if (!isDead()) {
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
	}

	public void removeColor(MonsterColor color) {
		
		for (MonsterListener l : listeners) {
			l.onRemoveColor(this, color);
		}
		
		MonsterColor prev = color.getPrevious();
		MonsterColor next = color.getNext();
		
		if (prev != null) {
			prev.setNext(null);
			
			if (next != null) {
				next.setPrevious(null);
			}
		}
		
		colors.remove(color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.globalgamejam.maze.Block#draw(com.badlogic.gdx.graphics.g2d.Batch)
	 */
	@Override
	public void draw(Batch batch) {
		
		if (angry && Math.random() < 0.001f) {
			SoundUtils.playRandomSound("aggro", 15);
		}
		
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

		Tween.to(sprite, SpriteTween.ROTATION, 0.105f).target(factor)
				.ease(TweenEquations.easeInBounce).start(tweenManager);

		super.draw(batch);
	}

	/*
	 * (non-Javadoc)
	 * 
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

		Tween.to(this, tweenType, moveInterval / 1000f)
				.target(0f).ease(TweenEquations.easeNone).start(manager);
	}

	public static class MonsterColor extends Color implements Indexable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object
		 */
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

		/*
		 * (non-Javadoc)
		 * 
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

		/*
		 * (non-Javadoc)
		 * 
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