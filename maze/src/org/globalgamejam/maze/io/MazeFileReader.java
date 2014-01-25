package org.globalgamejam.maze.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class MazeFileReader {
	
	public static final String PATH = "level/";

	public String[] read(String levelFile) throws IOException {
		
		FileHandle handle = Gdx.files.internal(PATH + levelFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(handle.read()));
		ArrayList<String> lines = new ArrayList<String>();
		String line = reader.readLine();
		
		do {
			if (line != null) {
				lines.add(line);
			}
		} while ((line = reader.readLine()) != null);
		
		reader.close();
		
		return lines.toArray(new String[lines.size()]);
	}
}
