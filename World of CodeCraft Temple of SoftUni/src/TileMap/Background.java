package TileMap;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {

    private BufferedImage image;

    private double x;
    private double y;
    private double dx;
    private double dy;

    private double moveScale;

    public Background(String s,double moveScale) {
        this.moveScale = moveScale;

        try{
            this.image = ImageIO.read(getClass().getResourceAsStream(s));
            this.moveScale = moveScale;
        }catch (Exception e){
            System.out.println("closed");
        }
    }

    public void setPosition(double x, double y){
        this.x = (x * moveScale)% GamePanel.WIDTH;
        this.y = (y * moveScale)% GamePanel.HEIGHT;
    }

    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    public void update(){
        x += dx;
        y += dy;
    }

    public void draw(Graphics2D g){
        g.drawImage(this.image, (int)this.x, (int)this.y, null);
        if(this.x < 0){
            g.drawImage(this.image, (int)this.x + GamePanel.WIDTH,(int)this.y, null);
        }else if(this.x > 0){
            g.drawImage(this.image, (int)this.x - GamePanel.WIDTH, (int)this.y, null);
        }

    }
}
