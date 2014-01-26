package org.globalgamejam.maze.ui;

import org.globalgamejam.maze.DungeonKeeper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DungeonMeter extends Actor {

    private DungeonKeeper target;
    
    private Sprite life, background;

private float padding;

private float currentValue;
    
    public DungeonMeter(DungeonKeeper target) {
            this.target = target;
            currentValue = 1f;
             Pixmap map = new Pixmap(128, 128, Format.RGBA8888);
             Color color = new Color(Color.BLACK);
             color.a = 0.3f;
     map.setColor(color);
     map.fill();

     Texture texture = new Texture(map);
     map.dispose();
     
     background = new Sprite(texture);
     
     map = new Pixmap(128, 128, Format.RGBA8888);
     map.setColor(Color.valueOf("330077"));
     map.fill();

     texture = new Texture(map);
     map.dispose();
     
     life = new Sprite(texture);
     
     padding = 5;
    }
    
     @Override
 public void draw(Batch batch, float parentAlpha) {
             
         super.draw(batch, parentAlpha);

         if (currentValue != target.getSmatchValue()) {

                                    getColor().a = 1f;

                 if (target.getSmatchValue() > currentValue) {
                         currentValue += ((target.getSmatchValue() - currentValue) / 5);

                         if (currentValue > target.getSmatchValue()) {
                                 currentValue = target.getSmatchValue();
                         }
                 } else {
                         currentValue -= ((currentValue - target.getSmatchValue()) / 5);

                     if (currentValue < 0) {
                             currentValue = 0;
                     }
                 }

         }
         
         background.setColor(getColor());
         background.setBounds(getX(), getY(), getWidth(), getHeight());
         background.draw(batch);

         float lifeWidth = getWidth() * currentValue - padding * 2;

         if (lifeWidth < 0) {
                 lifeWidth = 0;
         }
         
         if (target.canSmatch()) {
        	 life.setColor(getColor());
         } else {
        	 life.setColor(getColor().r, getColor().g, getColor().b, 0.3f);
         }
         life.setBounds(getX() + padding, getY() + padding, lifeWidth,
                         getHeight() - padding * 2);
         life.draw(batch);
        
 }
    
    
}