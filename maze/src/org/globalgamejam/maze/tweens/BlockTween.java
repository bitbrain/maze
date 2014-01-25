package org.globalgamejam.maze.tweens;

import org.globalgamejam.maze.Block;

import aurelienribon.tweenengine.TweenAccessor;

public class BlockTween implements TweenAccessor<Block> {
	
	public static final int OFFSET_X = 1;

	public static final int OFFSET_Y = 2;
	
	public static final int SCALE = 3;
	@Override
	public int getValues(Block target, int tweenType, float[] returnValues) {
		
		switch (tweenType) {
			case OFFSET_X:
				returnValues[0] = target.getOffsetX();
				return 1;
			case OFFSET_Y:
				returnValues[0] = target.getOffsetY();
				return 1;
			case SCALE:
				returnValues[0] = target.getScale();
				return 1;
		}
		
		return 0;
	}

	@Override
	public void setValues(Block target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case OFFSET_X:
			target.setOffsetX(newValues[0]);
			break;
		case OFFSET_Y:
			target.setOffsetY(newValues[0]);
			break;
		case SCALE:
			target.setScale(newValues[0]);
			break;
	}
	}

}
