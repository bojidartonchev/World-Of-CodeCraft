package GameState;


import Characters.Character;
import Main.GamePanel;
import TileMap.Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LoadCharacterState extends GameState {

    private Background bg;
    private BufferedImage image;
    private int currentChoice = 0;
    private Font font;
    private LinkedHashMap<String, String> characters; //change to Character
    private ArrayList<String> names = new ArrayList<>();
    public LoadCharacterState(GameStateManager gsm) {
        super(gsm);
        initialize();
    }


    @Override
    public void initialize() {
        this.characters = loadCharacters();
        this.names.addAll(characters.keySet());
        changeImage(this.characters.get(this.names.get(0)));
        this.bg = new Background("/Backgrounds/loadCharBg.png", 1);
        this.font = new Font("LifeCraft", Font.BOLD, 30);
    }

    @Override
    public void draw(Graphics2D g) {

        this.bg.draw(g);
        g.drawImage(image, GamePanel.WIDTH/3+50, GamePanel.HEIGHT/4-50, 263, 344, null);

        g.setFont(font);
        for (int i = 0; i < names.size(); i++) {
            if (i == currentChoice) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.YELLOW);
            }
            g.drawString(names.get(i), GamePanel.WIDTH-100-names.get(i).length()*15, GamePanel.HEIGHT/6 + i * 50);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {

        } else if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = names.size()-1;
            }

            changeImage(this.characters.get(this.names.get(currentChoice)));
        } else if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if(currentChoice==names.size()){
                currentChoice=0;
            }
            changeImage(this.characters.get(this.names.get(currentChoice)));
        }

    }

    @Override
    public void keyReleased(int k) {

    }

    private void changeImage(String currentClass) {
        try {
            image = ImageIO.read(new File("Resources\\CharacterImages\\"+currentClass.toLowerCase()+"_right.png"));
        } catch (IOException ex) {
            System.out.println("Error loading image");
        }
    }

    private LinkedHashMap<String, String> loadCharacters(){//change to Character
        ArrayList<String> paths = new ArrayList<>();
        LinkedHashMap<String,String> characters = new LinkedHashMap<>();
        String path = System.getProperty("user.home");
        path+="\\World of CodeCraft Data";
        try {
            Files.walk(Paths.get(path)).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    paths.add(filePath.toString().split("\\\\")[4]);
                }
            });
        }
        catch (Exception ex){
            System.out.println("No Characters");
        }
        for (String currentPath : paths) {
            String name = currentPath.substring(0,currentPath.indexOf("."));
            characters.put(name,generateCharacter(path+"\\"+currentPath));
        }
        return characters;
    }

    private String generateCharacter(String path){//change to Character
        ArrayList<String> data = new ArrayList<>();
        Character currentChar;
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line = br.readLine();
            data.add(line);

        }
        catch(IOException ex){
            System.out.println("Error");
        }
        //check for class and name here::::::;

        //change to Character
        if(data.get(0).contains("Mage")){
            return "Mage";
        }
        else if(data.get(0).contains("Rogue")){
            return "Rogue";
        }
    return "Paladin";
    }

}
