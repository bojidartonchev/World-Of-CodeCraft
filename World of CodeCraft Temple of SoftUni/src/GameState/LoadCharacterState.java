package GameState;


import Characters.Character;
import Characters.Mage;
import Characters.Rogue;
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
    private Character currentChar;
    private int currentChoice = 0;
    private Font font;
    private LinkedHashMap<String, Character> characters; //change to Character
    private ArrayList<String> names = new ArrayList<>();
    public LoadCharacterState(GameStateManager gsm) {

        super(gsm);
        this.bg = new Background("/Backgrounds/loadCharBg.png", 1);
        this.font = new Font("LifeCraft", Font.BOLD, 30);
    }

    @Override
    public void initialize() {
        initializeCharacters();
        //this.currentChoice=0;
        try {
            this.currentChar = this.characters.get(this.names.get(this.currentChoice));
            changeImage(this.currentChar);
        }
        catch(IndexOutOfBoundsException ex){
            System.out.println("No Characters found");
        }
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
            this.select();
        }
        else if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = names.size()-1;
            }
            try{
                this.currentChar = this.characters.get(this.names.get(currentChoice));
                changeImage(this.currentChar);
            }
            catch (IndexOutOfBoundsException ex){
                //stops exeptions when list in empty
            }
        }
        else if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if(currentChoice==names.size()){
                currentChoice=0;
            }
            try{
                this.currentChar = this.characters.get(this.names.get(currentChoice));
                changeImage(this.currentChar);
            }
            catch (IndexOutOfBoundsException ex){
                //stops exeptions when list in empty
            }

        }
        else if (k == KeyEvent.VK_ESCAPE) {
            this.gsm.setState(GameStateManager.MENU_STATE);
        }

    }

    @Override
    public void keyReleased(int k) {

    }

    private void initializeCharacters(){
        this.characters = loadCharacters();
        this.names = new ArrayList<>();
        this.names.addAll(characters.keySet());
    }

    private void changeImage(Character character) {
        String currentClass = character.getClass().getTypeName().toString();
        currentClass = currentClass.substring(currentClass.indexOf(".")+1,currentClass.length());
        try {
            image = ImageIO.read(new File("Resources\\CharacterImages\\"+currentClass.toLowerCase()+"_right.png"));
        } catch (IOException ex) {
            System.out.println("Error loading image");
        }
    }

    private LinkedHashMap<String, Character> loadCharacters(){//change to Character
        ArrayList<String> paths = new ArrayList<>();
        LinkedHashMap<String,Character> characters = new LinkedHashMap<>();
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

    private Character generateCharacter(String path){//change to Character
        ArrayList<String> data = new ArrayList<>();
        Character currentChar = null;
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while(line!=null){
                data.add(line);
                line = br.readLine();
            }
        }
        catch(IOException ex){
            System.out.println("Error");
        }
        //check for class and name here::::::;
        String levelData = data.get(2);
        int level =Integer.parseInt(levelData.substring(levelData.indexOf(":")+1));
        //change to Character
        if(data.get(1).contains("Mage")){

            currentChar= new Mage("magename",this.gsm.gameStates.get(GameStateManager.LEVEL_1_STATE).getTileMap(),53,level+1);
        }
        else if(data.get(1).contains("Rogue")){
            currentChar= new Rogue("roguename",this.gsm.gameStates.get(GameStateManager.LEVEL_1_STATE).getTileMap(),53,level+1);
        }

    return currentChar;
    }

    private void select() {

        this.gsm.gameStates.get(GameStateManager.LEVEL_1_STATE).setCharacter(this.currentChar);
        this.gsm.setState(this.currentChar.getState());

    }


}
