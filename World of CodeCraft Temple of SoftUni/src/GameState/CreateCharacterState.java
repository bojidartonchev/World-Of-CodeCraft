package GameState;

import TileMap.Background;

import javax.swing.*;
import java.awt.*;

public class CreateCharacterState extends GameState {

    private Background bg;
    private JTextField textbox;

    public CreateCharacterState(GameStateManager gsm){
        super(gsm);
        initialize();
    }

    @Override
    public void initialize() {

        this.bg = new Background("/Backgrounds/createCharBg.jpg", 1);
    }

    @Override
    public void draw(Graphics2D g) {
    bg.draw(g);
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
