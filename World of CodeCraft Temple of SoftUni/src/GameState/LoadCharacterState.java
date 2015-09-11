package GameState;

import TileMap.Background;

import java.awt.*;

public class LoadCharacterState extends GameState {

    private Background bg;

    public LoadCharacterState(GameStateManager gsm){
        super(gsm);
        initialize();
    }
    @Override
    public void initialize() {
        this.bg = new Background("/Backgrounds/loadCharBg.png", 1);
    }

    @Override
    public void draw(Graphics2D g) {
        this.bg.draw(g);
    }

    @Override
    public void update() {

    }

    @Override
    public void keyPressed(int k) {

    }

    @Override
    public void keyReleased(int k) {

    }
}
