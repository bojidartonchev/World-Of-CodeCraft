package Characters;

import Entity.Animation;
import Entity.Enemies.Enemy;
import Entity.MapObject;
import Entity.Spell;
import Interfaces.ICharacter;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Character extends MapObject implements ICharacter{

    // Player stuff
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;
    private String name;
    private int currentGameState;

    // Casting
    private boolean casting;
    private ArrayList<Spell> spells;
    private Spell spell;

    // Attacking
    private boolean attacking;
    private int attackRange;
    private int attackDamage;

    // Gliding
    private boolean gliding;

    // Animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {
            2, 8, 1, 2, 4, 2, 5
    };

    // Animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int CASTING = 5;
    private static final int ATTACKING = 6;
    private static final int DEFAULTLEVEL = 1;



    protected Character(String name, TileMap tileMap, int maxHealth){
        super(tileMap);
        this.setState(DEFAULTLEVEL);
        this.name = name;
        this.health = this.maxHealth = maxHealth;
        this.init();
        //this.spells = spells;
        //this.spell = spell;
    }

    protected Character(String name, TileMap tileMap, int maxHealth, int level){
        super(tileMap);
        this.setState(level);
        this.name = name;
        this.health = this.maxHealth = maxHealth;
        this.init();
        //this.spells = spells;
        //this.spell = spell;
    }


    private void init(){
        this.setWidth(100);
        this.setHeight(129);
        this.setCwidth(90);
        this.setCheight(100);

        this.setMoveSpeed(2.3);
        this.setMaxSpeed(5.6);
        this.setStopSpeed(0.4);
        this.setFallSpeed(0.15);
        this.setMaxFallSpeed(4.0);
        this.setJumpStart(-8.8);
        this.setStopJumpSpeed(0.3);

        this.setFacingRight(true);
        // load sprites // vse oshte nqmame gif za characterite
        loadSprites();
        this.setAnimation(new Animation());
        this.setCurrentAction(this.IDLE);
        this.getAnimation().setFrames(this.sprites.get(this.IDLE));
        this.getAnimation().setDelay(400);
    }

    public void loadSprites() {

        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Player/"+this.getClass().getSimpleName().toLowerCase()+"sprites.png"
                    )
            );

            this.sprites = new ArrayList<BufferedImage[]>();
            for(int i = 0; i < 7; i++) {

                BufferedImage[] bi =
                        new BufferedImage[this.numFrames[i]];

                for(int j = 0; j < this.numFrames[i]; j++) {

                    if(i != this.ATTACKING) {
                        bi[j] = spritesheet.getSubimage(
                                j * this.getWidth(),
                                i * this.getHeight(),
                                this.getWidth(),
                                this.getHeight()
                        );
                    }
                    else {
                        bi[j] = spritesheet.getSubimage(
                                j * this.getWidth() * 2,
                                i * this.getHeight(),
                                this.getWidth() * 2,
                                this.getHeight()
                        );
                    }

                }

                this.sprites.add(bi);

            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getMana() {
        return this.mana;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public int getHealth() {
        return this.health;
    }
    public int getState() {
        return this.currentGameState;
    }
    public String getName() {
        return this.name;
    }


    public void setState(int value) {
        this.currentGameState = value;
    }

    public void setCasting() {
        this.casting = true;
    }
    public void setAttacking() {
        this.attacking = true;
    }
    public void setGliding(boolean b) {
        this.gliding = b;
    }


    public void checkAttack(ArrayList<Enemy> enemies) {

        // Loop through enemies
        for (int i = 0; i < enemies.size(); i++) {

            Enemy e = enemies.get(i);

            // Attack
            if (this.attacking) {
                if (this.isFacingRight()) {

                    if (
                            e.getX() > this.getX() &&
                                    e.getX() <= this.getX() + this.attackRange &&
                                    e.getY() > this.getY() - this.getHeight() / 2 &&
                                    e.getY() < this.getY() + this.getHeight() / 2
                            ) {
                        e.hit(this.attackDamage);
                    }
                }else {
                    if(
                            e.getX() < this.getX() &&
                                    e.getX() > this.getX() - this.attackRange &&
                                    e.getY() > this.getY() - this.getHeight() / 2 &&
                                    e.getY() < this.getY() + this.getHeight() / 2
                            ){
                        e.hit(this.attackDamage);
                    }
                }
            }
            // spells
           // for (int j = 0; j < this.spells.size(); j++) {
           //     if(this.spells.get(j).intersects(e)){
           //         e.hit(this.spell.getDamage());
           //         this.spells.get(j).setHit();
           //         break;
           //     }
           // }
            // check for enemy collision
            if(intersects(e)){
                this.hit(e.getDamage());
            }
        }
    }

    public void hit(int damage){
        if(this.flinching) return;
        this.health -= damage;
        if(this.health < 0) this.health=0;
        if(this.health == 0) this.dead = true;
        this.flinching = true;
        this.flinchTimer = System.nanoTime();
    }


    private void getNextPosition() {

        // movement
        if(this.isLeft()) {
            this.setDx(this.getDx() - this.getMoveSpeed());
            if(this.getDx() < -this.getMaxSpeed()) {
                this.setDx(-this.getMaxSpeed());
            }
        }
        else if(this.isRight()) {
            this.setDx(this.getDx() + this.getMoveSpeed());
            if(this.getDx() > this.getMaxSpeed()) {
                this.setDx(this.getMaxSpeed());
            }
        }
        else {
            if(this.getDx() > 0) {
                this.setDx(this.getDx() - this.getStopSpeed());
                if(this.getDx() < 0) {
                    this.setDx(0);
                }
            }
            else if(this.getDx() < 0) {
                this.setDx(this.getDx() + this.getStopSpeed());
                if(this.getDx() > 0) {
                    this.setDx(0);
                }
            }
        }

        // cannot move while attacking, except in air
        if((this.getCurrentAction() == this.ATTACKING || this.getCurrentAction() == this.CASTING) &&
                        !(this.isJumping() || this.isFalling())) {
            this.setDx(0);
        }

        // jumping
        if(this.isJumping() && !this.isFalling()) {
            this.setDy(this.getJumpStart());
            this.setFalling(true);
        }

        // falling
        if(this.isFalling()) {

            if(this.getDy() > 0 && this.gliding) this.setDy(this.getDy() + this.getFallSpeed() * 0.1);
            else this.setDy(this.getDy() + this.getFallSpeed());

            if(this.getDy() > 0) this.setJumping(false);
            if(this.getDy() < 0 && !this.isJumping()) this.setDy(this.getDy() + this.getStopJumpSpeed());;

            if(this.getDy() > this.getMaxFallSpeed()) this.setDy(this.getMaxFallSpeed());

        }

    }

    public void update() {

        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(this.getXtemp(), this.getYtemp());

        // check attack has stopped
        if(this.getCurrentAction() == this.ATTACKING){
            if(this.getAnimation().hasPlayedOnce()) this.attacking = false;
        }if(this.getCurrentAction() == this.CASTING){
            if(this.getAnimation().hasPlayedOnce()) this.casting = false;
        }

        // spell attack
        this.mana += 10;
        if(this.mana > this.maxMana) this.mana = this.maxMana;
        if(this.casting && this.getCurrentAction() != this.CASTING){
            if(this.mana > this.spell.getManaCost()){
                this.mana -= this.spell.getManaCost();
                Spell temporarySpell = new Spell(this.getTileMap(), this.isFacingRight());
                temporarySpell.setPosition(this.getX(), this.getY());
                this.spells.add(temporarySpell);
            }
        }

        // update spells
       // for (int i = 0; i < this.spells.size(); i++) {
       //     this.spells.get(i).update();
       //     if(this.spells.get(i).shouldRemove()){
       //         this.spells.remove(i);
       //         i--;
       //     }
       // }

        // check done flinching
        if(this.flinching){
            long elapsed = (System.nanoTime() - this.flinchTimer) / 1000000;
            if(elapsed > 1000){
                this.flinching = false;
            }
        }

        // set animation
        if(this.attacking) {
            if(this.getCurrentAction() != this.ATTACKING) {
                this.setCurrentAction(this.ATTACKING);
                this.getAnimation().setFrames(this.sprites.get(this.ATTACKING));
                this.getAnimation().setDelay(50);
                this.setWidth(60);
            }
        }
        else if(this.casting) {
            if(this.getCurrentAction() != this.CASTING) {
                this.setCurrentAction(this.CASTING);
                this.getAnimation().setFrames(sprites.get(this.CASTING));
                this.getAnimation().setDelay(100);
                this.setWidth(30);
            }
        }
        else if(this.getDy() > 0) {
            if(this.gliding) {
                if(this.getCurrentAction() != this.GLIDING) {
                    this.setCurrentAction(this.GLIDING);
                    this.getAnimation().setFrames(sprites.get(this.GLIDING));
                    this.getAnimation().setDelay(100);
                    this.setWidth(30);
                }
            }
            else if(this.getCurrentAction() != this.FALLING) {
                this.setCurrentAction(this.FALLING);
                this.getAnimation().setFrames(this.sprites.get(this.FALLING));
                this.getAnimation().setDelay(100);
                this.setWidth(30);
            }
        }
        else if(this.getDy() < 0) {
            if(this.getCurrentAction() != this.JUMPING) {
                this.setCurrentAction(this.JUMPING);
                this.getAnimation().setFrames(this.sprites.get(this.JUMPING));
                this.getAnimation().setDelay(-1);
                this.setWidth(30);
            }
        }
        else if(this.isLeft() || this.isRight()) {
            if(this.getCurrentAction() != this.WALKING) {
                this.setCurrentAction(this.WALKING);
                this.getAnimation().setFrames(this.sprites.get(this.WALKING));
                this.getAnimation().setDelay(40);
                this.setWidth(30);
            }
        }
        else {
            if(this.getCurrentAction() != this.IDLE) {
                this.setCurrentAction(this.IDLE);
                this.getAnimation().setFrames(this.sprites.get(this.IDLE));
                this.getAnimation().setDelay(400);
                this.setWidth(30);
            }
        }

        this.getAnimation().update();

        // set direction
        if(this.getCurrentAction() != this.ATTACKING && this.getCurrentAction() != this.CASTING) {
            if(this.isRight()) this.setFacingRight(true);
            if(this.isLeft()) this.setFacingRight(false);
        }

    }

    public void draw(Graphics2D g) {

        setMapPosition();
        // draw spells
        //for (int i = 0; i < this.spells.size(); i++) {
        //    this.spells.get(i).draw(g);
        //}
        // draw player
        if(this.flinching) {
            long elapsed =
                    (System.nanoTime() - this.flinchTimer) / 1000000;
            if(elapsed / 100 % 2 == 0) {
                return;
            }
        }

        super.draw(g);

    }
}

