package org.globalgamejam.maze;

import java.util.HashMap;
import java.util.Map;

public final class Levels {

	public static final Map<Integer, String> levels;
	
	static {
		levels = new HashMap<Integer, String>();
		
		levels.put(1, "1.30.mz");
		levels.put(2, "crosshair.mz");
		levels.put(3, "distillate.mz");
		levels.put(4, "mirror.mz");
		levels.put(5, "ribbon.mz");
	}
}
