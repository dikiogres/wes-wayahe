package com.TCourse.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.TCourse.Manager.Content;
import com.TCourse.TileMap.TileMap;

public class Diamond extends Entity {

  BufferedImage[] sprites;
  
  private ArrayList<int[]> tileChanges;
  
  public Diamond(TileMap tm) {
    
    super(tm);
    
    width = 16;
    height = 16;
    cWidth = 12;
    cHeight = 12;
    
    sprites = Content.DIAMOND[0];
    animation.setFrames(sprites);
    animation.setDelay(10);
    
    tileChanges = new ArrayList<int[]>();
  }
  
  public void addChange(int[] i) {
    tileChanges.add(i);
  }
  
  public ArrayList<int[]> getChanges() {
    return tileChanges;
  }
  
  public void update() {
    animation.update();
  }
  
  public void draw(Graphics2D g) {
    super.draw(g);
  }
  
}
