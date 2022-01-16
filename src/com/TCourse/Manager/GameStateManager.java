// Contains list of GameStates
package com.TCourse.Manager;

import java.awt.Graphics2D;

import com.TCourse.GameState.GameState;
import com.TCourse.GameState.IntroState;
import com.TCourse.GameState.MenuState;
import com.TCourse.GameState.PauseState;
import com.TCourse.GameState.PlayState;
import com.TCourse.GameState.FinishState;

public class GameStateManager {

  private boolean paused;
  private PauseState pauseState;
  
  private GameState[] gameStates;
  private int currentState;
  private int previousState;
  
  public static final int NUM_STATES = 4;
  public static final int INTRO = 0;
  public static final int MENU = 1;
  public static final int PLAY = 2;
  public static final int FINISH = 3;
  
  public GameStateManager() {
    
    JukeBox.init();
    
    paused = false;
    pauseState = new PauseState(this);

    gameStates = new GameState[NUM_STATES];
    setState(INTRO);
    
  }
  
  public void setState(int i) {
    previousState = currentState;
    unloadState(previousState);
    currentState = i;
    if (i == INTRO) {
      gameStates[i] = new IntroState(this);
      gameStates[i].init();
    }
    else if (i == MENU) {
      gameStates[i] = new MenuState(this);
      gameStates[i].init();
    }
    else if (i == PLAY) {
      gameStates[i] = new PlayState(this);
      gameStates[i].init();
    }
    else if (i == FINISH) {
      gameStates[i] = new FinishState(this);
      gameStates[i].init();
    }
  }
  
  public void setPaused(boolean b) {
    paused = b;
  }

  public void unloadState(int i) {
    gameStates[i] = null;
  }
  
  public void update() {
    if (paused) {
      pauseState.update();
    }
    else if (gameStates[currentState] != null) {
      gameStates[currentState].update();
    }
  }
  
  public void draw(Graphics2D g) {
    if (paused) {
      pauseState.draw(g);
    }
    else if (gameStates[currentState] != null) {
      gameStates[currentState].draw(g);
    }
  }
  
}
