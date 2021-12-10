package com.TCourse.Manager;

import java.awt.Graphics2D;

import com.TCourse.GameState.GameState;
import com.TCourse.GameState.IntroState;
import com.TCourse.GameState.MenuState;

public class GameStateManager {
  
  private GameState[] gameStates;
  private int currentState;
  private int previousState;
  
  public static final int NUM_STATES = 4;
  public static final int INTRO = 0;
  public static final int MENU = 1;
  public static final int PLAY = 2;
  public static final int GAMEOVER = 3;
  
  public GameStateManager() {
    
    gameStates = new GameState[NUM_STATES];
    setState(MENU);
    
  }
  
  public void setState(int i) {
    previousState = currentState;
    unloadState(previousState);
    currentState = i;
    if(i == INTRO) {
      gameStates[i] = new IntroState(this);
      gameStates[i].init();
    }
    else if(i == MENU) {
      gameStates[i] = new MenuState(this);
      gameStates[i].init();
    }
  }
  
  public void unloadState(int i) {
    gameStates[i] = null;
  }
  
  public void update() {
    gameStates[currentState].update();
  }
  
  public void draw(Graphics2D g) {
    gameStates[currentState].draw(g);
  }
  
}
