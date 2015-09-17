package GameState;

import Main.GamePanel;

import java.util.ArrayList;

public class GameStateManager {

    public static final int MENU_STATE = 0;
    public static final int CREATE_NEW_CHARACTER_STATE = 1;
    public static final int LOAD_CHARACTER_STATE = 2;
    public static final int LEVEL_1_STATE = 3;
    ArrayList<GameState> gameStates;
    //index of the game state in the list
    private int currentState;
    protected GamePanel panel;



    public GameStateManager(GamePanel panel) {
        this.gameStates = new ArrayList<GameState>();
        this.currentState = MENU_STATE;
        this.gameStates.add(new MenuState(this));
        this.gameStates.add(new CreateCharacterState(this));
        this.gameStates.add(new LoadCharacterState(this));
        this.gameStates.add(new Level1State(this));

        this.panel = panel;
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
