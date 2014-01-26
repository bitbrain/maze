package org.globalgamejam.maze.ui;

import org.globalgamejam.maze.DungeonKeeper;
import org.globalgamejam.maze.util.Clock;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ScoreLabel extends Label {

//    private TweenManager tweenManager;
//
//    private DungeonKeeper player;
//
//    private int currentPoints;
//
//    private boolean fadingAllowed;
    
    private Clock clock;

    public ScoreLabel(Clock clock, DungeonKeeper player, TweenManager tweenManager,
                    LabelStyle style) {
            super("", style);
//            this.player = player;
//            this.tweenManager = tweenManager;
            getColor().a = 0.5f;
            this.clock = clock;
    }

    public void reset() {
            //currentPoints = 0;
            //tweenManager = null;
            getColor().a = 1f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

//            if (currentPoints < player.getPoints()) {
//                    if (fadingAllowed && tweenManager != null) {
//                            tweenManager.killTarget(this);
//                            Color c = getColor();
//                            setColor(c.r, c.g, c.b, 1f);
//                            Tween.to(this, ActorTween.ALPHA, 1).target(0.5f)
//                                            .ease(TweenEquations.easeInOutQuad).start(tweenManager);
//                    }
//
//                    currentPoints += ((player.getPoints() - currentPoints) / 5) + 1;
//
//                    if (currentPoints > player.getPoints()) {
//                            currentPoints = player.getPoints();
//                    }
//
//                    fadingAllowed = false;
//
//            } else {
//                    fadingAllowed = true;
//            }

            setText(clock.toString());
            super.draw(batch, parentAlpha);
    }
}
