package org.globalgamejam.maze;

import org.globalgamejam.maze.util.Direction;
import org.globalgamejam.maze.util.Updateable;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Monster extends Block implements Updateable {

	private MonsterLogic logic;
	
	private Direction direction;
	
	private Direction lastDirection;
	
	public Monster(int x, int y, Maze maze, MonsterLogic logic) {
		super(x, y, maze, BlockType.MONSTER);
		this.logic = logic;
		direction = Direction.NONE;
		lastDirection = direction;
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
		
		super.setPosition(x, y);
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
	
	

	/* (non-Javadoc)
	 * @see org.globalgamejam.maze.Block#draw(com.badlogic.gdx.graphics.g2d.Batch)
	 */
	@Override
	public void draw(Batch batch) {
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		
		switch (getDirection()) {
		case DOWN:
			sprite.setRotation(0f);
			break;
		case LEFT:
			sprite.setRotation(90f);
			break;
		case NONE:
			break;
		case RIGHT:
			sprite.setRotation(-90f);
			break;
		case UP:
			sprite.setRotation(180f);
			break;
		default:
			break;
		
		}
		
		super.draw(batch);
	}

	/* (non-Javadoc)
	 * @see org.globalgamejam.maze.Block#getTextureID()
	 */
	@Override
	public String getTextureID() {
		return Assets.MONSTER;
	}
	
	

}
