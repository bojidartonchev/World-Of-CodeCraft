package GameState;

import Main.GamePanel;
import TileMap.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MenuState extends GameState {

    private Background bg;
    private int currentChoice = 0;
    private String[] options = {
            "Create New Character",
            "Load Character",
            "Start",
            "Quit"
    };

    private Font font;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        try{
            this.bg = new Background("/Backgrounds/backgroud.jpg", 1);
            //this.bg.setVector(-0.1, 0);
            this.font = new Font("LifeCraft", Font.BOLD, 60);

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
            g.drawString(options[i], GamePanel.WIDTH/2-options[i].length()*15, GamePanel.HEIGHT/3 + i * 100);
        }

    }

    @Override
    public void update() {
        this.bg.update();
    }

    private void select() throws IOException{
        if(currentChoice == 0){
           //start
            try {
                ProcessBuilder pb = new ProcessBuilder("java", "-jar", "src\\Forms\\CreateCharacterForm.jar");
                Process p = pb.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Exception occured" + ex);
            }
        }
        if(currentChoice == 1){
            this.gsm.setState(GameStateManager.LOAD_CHARACTER_STATE);
        }
        if(currentChoice==2){
            this.gsm.setState(GameStateManager.LEVEL_1_STATE);
        }
        if(currentChoice == 3){
           //quit
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER){
            try {
                this.select();
            }
            catch (IOException ex){
                System.out.println("error occured in selection");
            }
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
