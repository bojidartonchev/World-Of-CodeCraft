package GameState;

import Characters.Character;
import TileMap.TileMap;

public abstract class GameState {
    private TileMap tileMap;
    protected GameStateManager gsm;
    private Character character;
    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void initialize();
    public abstract void draw(java.awt.Graphics2D g);
    public abstract void update();
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
    public TileMap getTileMap(){
        return this.tileMap;
    }
    public void setTileMap(TileMap value){
        this.tileMap = value;
    }

    public Character getCharacter() {
        return this.character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
