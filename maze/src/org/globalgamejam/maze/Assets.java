package org.globalgamejam.maze;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Assets extends AssetManager {
	
	// ============ TYPES ==================
	
	public static final String MONSTER = "monster.png";
	public static final String WALL = "wall.png";
	public static final String FLOOR = "floor.png";	
	
	
	public static final String FLARE = "flare.p";
	
	public static final String AGGRO_1 = "aggro1.ogg";
	public static final String AGGRO_2 = "aggro2.ogg";
	public static final String AGGRO_3 = "aggro3.ogg";
	public static final String AGGRO_4 = "aggro4.ogg";
	public static final String AGGRO_5 = "aggro5.ogg";
	public static final String AGGRO_6 = "aggro6.ogg";
	public static final String AGGRO_7 = "aggro7.ogg";
	public static final String AGGRO_8 = "aggro8.ogg";
	public static final String AGGRO_9 = "aggro9.ogg";
	public static final String AGGRO_10 = "aggro10.ogg";
	public static final String AGGRO_11 = "aggro11.ogg";
	public static final String AGGRO_12 = "aggro12.ogg";
	public static final String AGGRO_13 = "aggro13.ogg";
	public static final String AGGRO_14 = "aggro14.ogg";
	public static final String AGGRO_15 = "aggro15.ogg";	
	public static final String KILL = "kill2.ogg";	
	public static final String RUN = "runfaster.ogg";
	public static final String DRIP = "dripMenuLeft.ogg";
	

	public static final String MENU = "menuWummern.ogg";
	
	

	
	private static Assets instance;
	
	static {
		instance = new Assets();
		instance.setLoader(ParticleEffect.class, new ParticleLoader(new InternalFileHandleResolver()));
	}
	
	private Assets() { }
	
	public static Assets getInstance() {
		return instance;
	}
	
	public void loadAll() {
		load(MONSTER, Texture.class);
		load(WALL, Texture.class);
		load(FLOOR, Texture.class);
		load(FLARE, ParticleEffect.class);
		load(AGGRO_1, Sound.class);
		load(AGGRO_2, Sound.class);
		load(AGGRO_3, Sound.class);
		load(AGGRO_4, Sound.class);
		load(AGGRO_5, Sound.class);
		load(AGGRO_6, Sound.class);
		load(AGGRO_7, Sound.class);
		load(AGGRO_8, Sound.class);
		load(AGGRO_9, Sound.class);
		load(AGGRO_10, Sound.class);
		load(AGGRO_11, Sound.class);
		load(AGGRO_12, Sound.class);
		load(AGGRO_13, Sound.class);
		load(AGGRO_14, Sound.class);
		load(AGGRO_15, Sound.class);
		load(KILL, Sound.class);
		load(RUN, Sound.class);
		load(DRIP, Sound.class);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.assets.AssetManager#get(java.lang.String)
	 */
	@Override
	public synchronized <T> T get(String fileName) {
		this.finishLoading();
		return super.get(fileName);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.assets.AssetManager#get(java.lang.String, java.lang.Class)
	 */
	@Override
	public synchronized <T> T get(String fileName, Class<T> type) {
		this.finishLoading();
		return super.get(fileName, type);
	}
	
	

}
