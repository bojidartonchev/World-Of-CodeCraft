package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Characters.Character;

public class HUD {
    // coordinates
    private final int X = 0;
    private final int Y = 5;
    private final int X_PLAYER_HEALTH = 25;
    private final int Y_PLAYER_HEALTH  = 20;
    private final int X_PLAYER_FIRE_LEVEL = 30;
    private final int Y_PLAYER_FIRE_LEVEL  = 41;
    // data
    private Character player;
    private BufferedImage image;
    private Font font;

    private int fontSize = 14;

    public HUD(Character player){

        this.player = player;

        loadImage();

        this.font = new Font("Arial", Font.PLAIN, fontSize);
    }

    private void loadImage() {
        try{
            this.image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics){
        graphics.drawImage(this.image, this.X, this.Y, null);
        graphics.setFont(this.font);
        graphics.setColor(Color.WHITE);

        String playerHealthStatus = String.format("%d/%d", this.player.getHealth(), this.player.getMaxHealth());
        String playerManaStatus =  String.format("%d/%d", this.player.getMana(), this.player.getMaxMana());

        graphics.drawString(playerHealthStatus, X_PLAYER_HEALTH, Y_PLAYER_HEALTH);
        graphics.drawString(playerManaStatus, X_PLAYER_FIRE_LEVEL, Y_PLAYER_FIRE_LEVEL);
    }
}
