package org.globalgamejam.maze.graphics;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;

public class ParticleManager {

	 // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    
    private Map<ParticleEffect, Boolean> effects;

    // ===========================================================
    // Constructors
    // ===========================================================
    
    public ParticleManager() {
            effects = new ConcurrentHashMap<ParticleEffect, Boolean>();
    }

    // ===========================================================
    // Getters and Setters
    // ===========================================================

    // ===========================================================
    // Methods from Superclass
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================
    
    public ParticleEffect create(ParticleEffect original, boolean endless) {
            ParticleEffect copy = new ParticleEffect(original);                
            effects.put(copy, endless);
            return copy;
    }
    
    public void setColor(ParticleEffect effect, float[] colors, float[] timeline) {
            for (ParticleEmitter emitter : effect.getEmitters()) {
                    emitter.getTint().setTimeline(timeline);
                    emitter.getTint().setColors(colors);
            }
    }
    
    public void setParticleCount(ParticleEffect effect, int count) {
            for (ParticleEmitter emitter : effect.getEmitters()) {
                    emitter.setMaxParticleCount(count);
            }
    }
    
    public int getParticleCount(ParticleEffect effect) {
            
            int count = 0;
            
            for (ParticleEmitter emitter : effect.getEmitters()) {
                    if (count < emitter.getMaxParticleCount()) {
                            count = emitter.getMaxParticleCount();
                    }
            }
            
            return count;
    }
    
    public void render(Batch batch, float delta) {
            
            for (Entry<ParticleEffect, Boolean> entries : effects.entrySet()) {
                    
                    if (!entries.getValue() && entries.getKey().isComplete()) {
                            ParticleEffect effect = entries.getKey();
                            effects.remove(effect);
                    } else {                                
                            entries.getKey().draw(batch, delta);
                    }
            }
    }
    
    public void unload(ParticleEffect effect) {
            effects.remove(effect);
    }
    
    public void setEndless(ParticleEffect effect, boolean endless) {
            
            if (effect != null) {
                    effects.put(effect, endless);
                    
                    for (ParticleEmitter emitter : effect.getEmitters()) {
                            emitter.setContinuous(endless);
                    }
            }
    }
    
    public void clear() {
            for (Entry<ParticleEffect, Boolean> entries : effects.entrySet()) {
                    entries.getKey().setDuration(0);
            }
            
            effects.clear();
    }
}
