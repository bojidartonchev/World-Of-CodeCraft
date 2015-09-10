package GameState;

import java.util.ArrayList;

public class GameStateManager {

    public final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;
    ArrayList<GameState> gameStates;
    //index of the game state in the list
    private int currentState;



    public GameStateManager() {
        this.gameStates = new ArrayList<GameState>();
        this.currentState = MENUSTATE;
        this.gameStates.add(new MenuState(this));
        this.gameStates.add(new Level1State(this));
    }

    public void setState(int state) {
        currentState = state;
        gameStates.get(currentState).initialize();
    }

    public void update(){
        gameStates.get(currentState).update();
    }

    public void draw(java.awt.Graphics2D g){
        gameStates.get(currentState).draw(g);
    }

    public void keyPressed(int k){
        gameStates.get(currentState).keyPressed(k);
    }

    public void keyReleased(int k){
        gameStates.get(currentState).keyReleased(k);
    }

}
