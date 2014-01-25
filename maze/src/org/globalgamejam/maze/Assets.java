package org.globalgamejam.maze;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Assets extends AssetManager {
	
	// ============ TYPES ==================
	
	public static final String MONSTER = "monster.png";
	public static final String WALL = "wall.png";
	public static final String FLOOR = "floor.png";
	
	
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
