package com.TCourse.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.TCourse.Manager.Content;
import com.TCourse.TileMap.TileMap;

public class Item extends Entity{

  private BufferedImage sprite;
  private int type;
  
  public static final int BOAT = 0;
  public static final int AXE = 1;
  
  public Item(TileMap tm) {
    super(tm);
    type = -1;
    width = height = 16;
    cWidth = cHeight = 12;
  }
  
  public void setType(int i) {
    type = i;
    if (type == BOAT) {
      sprite = Content.ITEMS[1][0];
    }
    else if (type == AXE) {
      sprite = Content.ITEMS[1][1];
    }
  }
  
  public void collected(Player p) {
    if (type == BOAT) {
      p.gotBoat();
    }
    if (type == AXE) {
      p.gotAxe();
    }
  }
  
  public void draw(Graphics2D g) {
    setMapPosition();
    g.drawImage(sprite, x + xMap - width / 2, y + yMap - height / 2, null);
  }
  
}
