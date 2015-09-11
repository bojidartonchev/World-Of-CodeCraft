package Entity.Enemies;


import Entity.Animation;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HelmetEnemy extends EnemyBase {

    private BufferedImage[] sprites;

    private final int numberOfSprites = 3;
    private final long animationDelay = 300;

    public HelmetEnemy(TileMap tileMap){
        super(tileMap);

        this.moveSpeed = 0.3;
        this.maxSpeed = 0.3;
        this.fallSpeed = 0.2;
        this.maxFallSpeed = 10.0;

        this.maxHealth = 2;
        this.currentHealth = maxHealth;

        this.damage = 1;

        // sprite dimentions
        this.width = 30;
        this.height = 30;
        this.cwidth = 20;
        this.cheight = 20;



        loadSprites();
        setAnimation();
    }

    private void setAnimation() {
        this.animation = new Animation();
        this.animation.setFrames(sprites);
        this.animation.setDelay(animationDelay);
        this.right = true;
    }

    private void loadSprites() {
        try {
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Enemies/helmet-enemy.gif"));
            this.sprites = new BufferedImage[numberOfSprites];
            for (int i = 0; i < sprites.length; i++) {

                this.sprites[i] = spriteSheet.getSubimage(i * this.width, 0, this.width, this.height);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void goToNextPosition() {

        // movement
        if(this.left){
            this.dx -= this.moveSpeed;
            if(this.dx < -this.maxSpeed){
                this.dx = -this.maxSpeed;
            }
        }
        else if(this.right){
            this.dx += moveSpeed;
            if(this.dx > this.maxSpeed){
                this.dx = this.maxSpeed;
            }
        }

        // falling
        if(this.falling){
            this.dy += this.fallSpeed;
        }
    }

    public void update(){

        // update position
        goToNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        // check if flinching
        if(this.isFlinching){
            long elapsedTime = (System.nanoTime() - flinchTimer) / 1_000_000;

            if(elapsedTime > 400){
                isFlinching = false;
            }
        }

        // if hits a wall, change direction to opposite
        if(this.right  && dx == 0){
            this.right = false;
            this.left = true;
        }

        if(left && dx == 0){
            this.left = false;
            this.right = true;
        }

        // update animation
        this.animation.update();
    }

    public void draw(Graphics2D graphics){
        if(notOnScreen()){
            return;
        }

        setMapPosition();

        super.draw(graphics);
    }
}
