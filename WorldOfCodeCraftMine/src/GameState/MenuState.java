package GameState;

import Main.GamePanel;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {

    private Background bg;
    private int currentChoice = 0;
    private String[] options = {
            "Start",
            "Help",
            "Quit"
    };

    private Font font;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        try{
            this.bg = new Background("/Backgrounds/backgroud.jpg", 1);
            //this.bg.setVector(-0.1, 0);
            this.font = new Font("LifeCraft", Font.BOLD, 100);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize() {

    }

    @Override
    public void draw(Graphics2D g) {
        //draw background
        this.bg.draw(g);


        //draw menu options
        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if(i == currentChoice){
                g.setColor(Color.green);
            }else{
                g.setColor(Color.YELLOW);
            }
            g.drawString(options[i], GamePanel.WIDTH/2-100, GamePanel.HEIGHT/3 + i * 150);
        }

    }

    @Override
    public void update() {
        this.bg.update();
    }

    private void select(){
        if(currentChoice == 0){
           //start
            this.gsm.setState(GameStateManager.LEVEL1STATE);
        } if(currentChoice == 1){
           //help
        } if(currentChoice == 2){
           //quit
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER){
            this.select();
        }else if(k == KeyEvent.VK_UP){
            currentChoice--;
            if (currentChoice == -1){
                currentChoice = options.length-1;
            }
        }else if(k == KeyEvent.VK_DOWN){
            currentChoice++;
            if(currentChoice == options.length){
                currentChoice = 0;
            }
        }

    }

    @Override
    public void keyReleased(int k) {

    }
}
