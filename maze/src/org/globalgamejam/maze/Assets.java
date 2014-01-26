package org.globalgamejam.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Assets extends AssetManager {
	
	// ============ TYPES ==================
	
	public static final String MONSTER = "monster.png";
	public static final String WALL = "wall.png";
	public static final String GAMEOVER = "gameover.png";
	public static final String MINIONS = "minions.png";
	public static final String FLOOR = "floor.png";	
	public static final String LOGO = "title.png";	
	public static final String BACKGROUND = "background.png";	
	
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
	public static final String RUN = "runMaybe.ogg";
	public static final String RUNFASTER = "runfaster.ogg";
	public static final String DRIP = "dripMenuLeft.ogg";
	public static final String INGAME = "HPmusik.ogg";
	public static final String MENU = "menuWummern.ogg";
	
	public static final String MU = "mu.ogg";	
	public static final String SPLASH = "splash.ogg";	
	public static final String KILL1 = "kill1.ogg";	
	public static final String KILL2 = "kill2.ogg";	
	
	public static final String DIER1 = "dier1.ogg";
	public static final String DIER2 = "dier2.ogg";
	public static final String DIER3 = "dier3.ogg";
	public static final String DIER4 = "dier4.ogg";
	public static final String DIER5 = "dier5.ogg";
	public static final String MAZEVOICE = "mazevoicedemo.ogg";
	
	public static final String DIEC1 = "diec1.ogg";
	public static final String DIEC2 = "diec2.ogg";
	public static final String DIEC3 = "diec3.ogg";
	public static final String DIEC4 = "diec4.ogg";
	
	public static final String VOICEC1 = "voicec1.ogg";
	public static final String VOICEC2 = "voicec2.ogg";
	public static final String VOICEC3 = "voicec3.ogg";
	public static final String VOICEC4 = "voicec4.ogg";
	public static final String VOICEC5 = "voicec5.ogg";
	public static final String GAMEOVERSOUND = "gameoversound.ogg";
	
	public static BitmapFont FONT;

	
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
		load(GAMEOVER, Texture.class);
		load(LOGO, Texture.class);
		load(BACKGROUND, Texture.class);
		load(MINIONS, Texture.class);
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
		load(KILL1, Sound.class);
		load(KILL2, Sound.class);
		load(RUN, Sound.class);
		load(RUNFASTER, Sound.class);
		load(MU, Sound.class);
		load(SPLASH, Sound.class);
		
		load(DIER1, Sound.class);
		load(DIER2, Sound.class);
		load(DIER3, Sound.class);
		load(DIER4, Sound.class);
		load(DIER5, Sound.class);
		
		load(DIEC1, Sound.class);
		load(DIEC2, Sound.class);
		load(DIEC3, Sound.class);
		load(DIEC4, Sound.class);
		
		load(VOICEC1, Sound.class);
		load(VOICEC2, Sound.class);
		load(VOICEC3, Sound.class);
		load(VOICEC4, Sound.class);
		load(VOICEC5, Sound.class);
		
		load(MAZEVOICE, Sound.class);
		
		load(DRIP, Music.class);
		load(INGAME, Music.class);
		load(MENU, Music.class);
		load(GAMEOVERSOUND, Music.class);
		
		FONT = new BitmapFont(Gdx.files.internal("medium.fnt"));
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
