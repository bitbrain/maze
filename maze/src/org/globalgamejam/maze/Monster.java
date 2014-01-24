package org.globalgamejam.maze;

import org.globalgamejam.maze.util.Updateable;

public class Monster extends Block implements Updateable {

	private MonsterLogic logic;
	
	public Monster(int x, int y, Maze maze, MonsterLogic logic) {
		super(x, y, maze, BlockType.MONSTER);
		this.logic = logic;
	}
	
	@Override
	public void update(float delta) {
		logic.update(delta, this);
	}

	/* (non-Javadoc)
	 * @see org.globalgamejam.maze.Block#getTextureID()
	 */
	@Override
	public String getTextureID() {
		return Assets.MONSTER;
	}
	
	

}
