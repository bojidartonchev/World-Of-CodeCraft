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
        setPosition(this.getXtemp(), this.getYtemp());

        // check if flinching
        if(this.isFlinching()){
            long elapsedTime = (System.nanoTime() - getFlinchTimer()) / 1_000_000;

            if(elapsedTime > 400){
                setFlinching(false);
            }
        }

        // if hits a wall, change direction to opposite
        if(this.isRight()  && this.getDx() == 0){

            this.setRight(false);
            this.setLeft(true);
            this.setFacingRight(false);
        }

        else if(this.isLeft() && this.getDx() == 0){
            this.setLeft(false);
            this.setRight(true);
            this.setFacingRight(true);
        }

        // this.setDx(0.2); todo : kinda works
        // update animation
        this.getAnimation().update();
    }

    protected void setAnimation(long animationDelay) {
        this.setAnimation(new Animation());
        this.getAnimation().setFrames(sprites);
        this.getAnimation().setDelay(animationDelay);
    }

    protected void loadSprites(String path, int numberOfSprites) {
        try {
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(path)); // path
            this.sprites = new BufferedImage[numberOfSprites];
            for (int i = 0; i < sprites.length; i++) {
                try {
                    this.sprites[i] = spriteSheet.getSubimage(i * this.getWidth(), 0, this.getWidth(), this.getHeight());
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
        if(this.isLeft()){
            this.setDx(this.getDx() - this.getMoveSpeed()); // getDX = 0 ?
            if(this.getDx() < -this.getMaxSpeed()){
                this.setDx(- this.getMaxSpeed());
            }
        }
        else if(this.isRight()){
            this.setDx(this.getDx() + this.getMoveSpeed());
            if(this.getDx() > this.getMaxSpeed()){
                this.setDx(this.getMaxSpeed());
            }
        }

        // falling
        if(this.isFalling()){
            this.setDy(this.getDy() + this.getFallSpeed());
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
