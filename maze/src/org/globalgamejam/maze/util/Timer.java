package org.globalgamejam.maze.util;

import java.util.concurrent.TimeUnit;

public class Timer {
    
    private long startTime;

    private boolean running;
    
    private long pauseTime;
    
    private long currentTicks;
    
    public Timer() {
            reset();
            running = false;
    }
    
    @Override
    public String toString() {
            return convertValue(TimeUnit.MILLISECONDS.toMinutes(getTicks())) + ":" +
            convertValue(TimeUnit.MILLISECONDS.toSeconds(getTicks()) - 
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getTicks()))
                    );
    }

    // ===========================================================
    // Methods
    // ===========================================================
    
    public void start() {
            running = true;
            
            if (pauseTime > 0) {
                    startTime = System.currentTimeMillis() - pauseTime;
                    pauseTime = 0;
            }
    }
    
    public void stop() {
            running = false;
            reset();
    }
    
    public void pause() {
            pauseTime = getTicks();
            running = false;
    }
    
    public void reset() {
            startTime = System.currentTimeMillis();
            currentTicks = 0;
    }
    
    public long getTicks() {
            
            if (running) {
                    currentTicks = System.currentTimeMillis() - startTime;
            }
            
            return currentTicks;
    }
    
    public boolean isRunning() {
            return running;
    }

    private String convertValue(long time) {
            if (time < 10) {
                    return "0" + time;
            } else {
                    return "" + time;
            }
    }
    

}