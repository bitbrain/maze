package org.globalgamejam.maze;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Maze - The Game";
		cfg.useGL20 = true;
		cfg.fullscreen = true;
		
		new LwjglApplication(new MazeGame(), cfg);
	}
}
