package Entity.Enemies;

import Entity.Animation;
import Entity.MapObject;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;

public abstract class Enemy extends MapObject {
    private EnemyType type;
    private BufferedImage[] sprites;

    private boolean isDead;
    private int damage;

    private boolean isFlinching;
    private long flinchTimer;

    private int currentHealth;
    private int maxHealth;

    protected Enemy(TileMap tileMap, EnemyType type){
        super(tileMap);
        this.type = type;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    protected void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    protected void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public boolean isFlinching() {
        return isFlinching;
    }

    protected void setFlinching(boolean isFlinching) {
        this.isFlinching = isFlinching;
    }

    public long getFlinchTimer() {
        return flinchTimer;
    }

    protected void setFlinchTimer(long flinchTimer) {
        this.flinchTimer = flinchTimer;
    }

    public boolean isDead(){
        return  this.isDead;
    }

    public int getDamage(){
        return this.damage;
    }

    protected void setDamage(int damage){
        this.damage = damage;
    }

    public void hit(int damage){
        if(isDead || isFlinching){
            return;
        }

        this.currentHealth-=damage;

        if(this.currentHealth <= 0){
            this.currentHealth = 0;
            this.isDead = true;
        }

        this.isFlinching = true;
        this.flinchTimer = System.nanoTime();
    }

    public  void update(){
        // update position
        goToNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        // check if flinching
        if(this.isFlinching()){
            long elapsedTime = (System.nanoTime() - getFlinchTimer()) / 1_000_000;

            if(elapsedTime > 400){
                setFlinching(false);
            }
        }

        // if hits a wall, change direction to opposite
        if(this.right  && dx == 0){
            this.right = false;
            this.left = true;
            this.facingRight = false;
        }

        else if(left && dx == 0){
            this.left = false;
            this.right = true;
            this.facingRight = true;
        }
        // update animation
        this.animation.update();
    }

    protected void setAnimation(long animationDelay) {
        this.animation = new Animation();
        this.animation.setFrames(sprites);
        this.animation.setDelay(animationDelay);
    }

    protected void loadSprites(String path, int numberOfSprites) {
        try {
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(path)); // path
            this.sprites = new BufferedImage[numberOfSprites];
            for (int i = 0; i < sprites.length; i++) {
                try {
                    this.sprites[i] = spriteSheet.getSubimage(i * this.width, 0, this.width, this.height);
                }catch (RasterFormatException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void goToNextPosition() {

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

    @Override
    public void draw(Graphics2D graphics){
        if(notOnScreen()){
            return;
        }

        setMapPosition();

        super.draw(graphics);
    }

}
