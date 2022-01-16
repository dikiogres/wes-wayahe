// State when finish the game yeay!
// under 5 minutes: summa cumlaude
// under 6 minutes: magna cumlaude
// under 7 minutes: cumlaude
// For 8 minutes or greater: high merit

package com.TCourse.GameState;

import java.awt.Color;
import java.awt.Graphics2D;

import com.TCourse.Main.GamePanel;
import com.TCourse.Manager.Content;
import com.TCourse.Manager.DataTime;
import com.TCourse.Manager.GameStateManager;
import com.TCourse.Manager.JukeBox;
import com.TCourse.Manager.Keys;

public class FinishState extends GameState {
  
  private Color color;
  
  private int rank;
  private long ticks;
  
  public FinishState(GameStateManager gsm) {
    super(gsm);
  }
  
  public void init() {
    color = new Color(164, 198, 222);
    ticks = DataTime.getTime();
    if (ticks < 9000) rank = 1;
    else if (ticks < 10800) rank = 2;
    else if (ticks < 12600) rank = 3;
    else rank = 4;
  }
  
  public void update() {
    handleInput();
  }
  
  public void draw(Graphics2D g) {
    
    g.setColor(color);
    g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT_TOTAL);
    
    Content.drawString(g, "finish time", 20, 36);
    
    int minutes = (int) (ticks / 1800);
    int seconds = (int) ((ticks / 30) % 60);
    if (minutes < 10) {
      if (seconds < 10) Content.drawString(g, "0" + minutes + ":0" + seconds, 44, 48);
      else Content.drawString(g, "0" + minutes + ":" + seconds, 44, 48);
    }
    else {
      if (seconds < 10) Content.drawString(g, minutes + ":0" + seconds, 44, 48);
      else Content.drawString(g, minutes + ":" + seconds, 44, 48);
    }
    
    Content.drawString(g, "rank", 48, 66);
    if (rank == 1) Content.drawString(g, "summa cumlaude", 8, 78);
    else if (rank == 2) Content.drawString(g, "magna cumlaude", 8, 78);
    else if (rank == 3) Content.drawString(g, "cumlaude", 32, 78);
    else if (rank == 4) Content.drawString(g, "high merit", 24, 78);
    
    Content.drawString(g, "press enter", 20, 110);
    
  }
  
  public void handleInput() {
    if (Keys.isPressed(Keys.ENTER)) {
      gsm.setState(GameStateManager.MENU);
      JukeBox.play("collect_book");
    }
  }
  
}