package org.globalgamejam.maze.util;

import org.globalgamejam.maze.Assets;

import com.badlogic.gdx.audio.Sound;

public final class SoundUtils {

	
	public static void playRandomSound(String source, int count) {
		RandomBag<Sound> sounds = new RandomBag<Sound>();

		for (int i = 1; i <= count; ++i) {
			String name = source + i + ".ogg";
			Assets.getInstance().finishLoading();
			Sound sound = Assets.getInstance().get(name, Sound.class);
			sounds.put(sound);
		}

		Sound random = sounds.fetch();

		random.play(1f, (float) (Math.random() * 0.5f + 0.5), 1f);
	}
}
