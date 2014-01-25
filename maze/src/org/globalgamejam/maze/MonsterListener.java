package org.globalgamejam.maze;

import org.globalgamejam.maze.Monster.MonsterColor;

public interface MonsterListener {

	void onMove(Monster monster, int oldX, int oldY);
	
	void onRemoveColor(Monster monster, MonsterColor color);
	
	void onCreateColor(Monster monster, MonsterColor color);
}
