package com.TCourse.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.TCourse.Main.GamePanel;
import com.TCourse.Manager.GameStateManager;
import com.TCourse.Manager.Keys;

public class MenuState extends GameState{
  
  private BufferedImage background;
  private BufferedImage course;
  
  private int currentOption = 0;
  private Font font = new Font("/Content/StayPixelRegular-EaOxl.ttf", 1, 17);
  
  private String[] options = {
    "START",
    "QUIT"
  };
  
  public MenuState(GameStateManager gsm) {
    super(gsm);
  }
  
  public void init() {
    try {
      background = ImageIO.read(getClass().getResourceAsStream("/Content/menuscreen.gif"));
//      course = ImageIO.read(getClass().getResourceAsStream("/Logo/java_logo.jpg"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void update() {
    handleInput();
  }
  
  public void draw(Graphics2D g) {
    
    g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
    
    g.setFont(font);
    g.drawString(options[0], 40, 90);
    g.drawString(options[1], 40, 110);
    
    if (currentOption == 0) g.drawImage(course, 25, 86, null);
    else if (currentOption == 1) g.drawImage(course, 25, 96, null);
    
  }
  
  public void handleInput() {
    if (Keys.isPressed(Keys.DOWN) && currentOption < options.length - 1) {
      currentOption++;
    }
    if (Keys.isPressed(Keys.UP) && currentOption > 0) {
      currentOption--;
    }
    if (Keys.isPressed(Keys.ENTER)) {
      selectOption();
    }
  }
  
  private void selectOption() {
    if (currentOption == 0) {
      gsm.setState(GameStateManager.PLAY);
    }
    if (currentOption == 1) {
      System.exit(0);
    }
  }
  
}
