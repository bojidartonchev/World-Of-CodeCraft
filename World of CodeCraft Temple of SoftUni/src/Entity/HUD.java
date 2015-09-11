//package Entity;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//public class HUD { //TODO: CREATE PLAYER CLASS
//    // coordinates
//    private final int X = 0;
//    private final int Y = 5;
//    private final int X_PLAYER_HEALTH = 25;
//    private final int Y_PLAYER_HEALTH  = 20;
//    private final int X_PLAYER_FIRE_LEVEL = 20;
//    private final int Y_PLAYER_FIRE_LEVEL  = 20;
//    // data
//    //private Player player;
//    private BufferedImage image;
//    private Font font;
//
//    private int fontSize = 14;
//
//    public HUD(Player playeer){
//
//       this.player = player;
//
//        loadImage();
//
//        this.font = new Font("Arial", Font.PLAIN, fontSize);
//    }
//
//    private void loadImage() {
//        try{
//            this.image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    public void draw(Graphics2D graphics){
//        graphics.drawImage(this.image, this.X, this.Y, null);
//        graphics.setFont(this.font);
//        graphics.setColor(Color.WHITE);
//
//        String playerHealth = String.format("%d/%d", this.player.getHealth, this.player.getMaxHealth);
//        String playerFireLevel =  String.format("%d/%d", this.player.getFire, this.player.getMaxFire);
//
//        graphics.drawString(playerHealth, X_PLAYER_HEALTH, Y_PLAYER_HEALTH);
//        graphics.drawString(playerFireLevel, X_PLAYER_FIRE_LEVEL, Y_PLAYER_FIRE_LEVEL);
//    }
//}
