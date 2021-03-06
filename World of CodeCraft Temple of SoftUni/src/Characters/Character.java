package Characters;

import Entity.Animation;
import Entity.Enemies.Enemy;
import Entity.MapObject;
import Entity.Spell;
import Interfaces.Attackable;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Character extends MapObject implements Attackable {

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


    // Attacking
    private boolean attacking;
    private int attackRange;
    private int attackDamage;

    // Gliding
    private boolean gliding;

    // Animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {
            1, 4, 1, 2, 4, 1, 5
    };

    // Animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int CASTING = 5;
    private static final int ATTACKING = 6;
    private static final int DEFAULT_LEVEL = 1;



    protected Character(String name, TileMap tileMap, int maxHealth){
        super(tileMap);
        this.setState(DEFAULT_LEVEL);
        this.name = name;
        this.health = this.maxHealth = maxHealth;
        this.initCharacter(name, maxHealth);
        initSpells();
    }

    protected Character(String name, TileMap tileMap, int maxHealth, int level){
        super(tileMap);
        this.setState(level);

        this.initCharacter(name, maxHealth);
        initSpells();

    }

    private void initSpells() {
        this.spells = new ArrayList<>();
        Spell spell = new Spell(this.getTileMap(),true);
        this.spells.add(spell);
    }


    private void initCharacter(String name, int maxHealth){
        this.name = name;
        this.health = this.maxHealth = maxHealth;

        this.setWidth(100);
        this.setHeight(122);
        this.setCwidth(90);
        this.setCheight(100);

        this.setMoveSpeed(2.3);
        this.setMaxSpeed(5.6);
        this.setStopSpeed(0.4);
        this.setFallSpeed(0.15);
        this.setMaxFallSpeed(6.0);
        this.setJumpStart(-8.8);
        this.setStopJumpSpeed(0.5);

        this.setAttackDamage(8);
        this.setAttackRange(40);
        this.setMana(2500);
        this.setMaxMana(2500);

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


                    if(i==this.ATTACKING||i==this.CASTING||i==this.JUMPING||i==this.GLIDING||i==this.FALLING) {
                        bi[j] = spritesheet.getSubimage(
                                j * this.getWidth()*2,
                                i * this.getHeight(),
                                this.getWidth()*2,
                                this.getHeight()
                        );
                    }
                    else  {
                        bi[j] = spritesheet.getSubimage(
                                j * this.getWidth(),
                                i * this.getHeight(),
                                this.getWidth(),
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

    public boolean isFlinching() {
        return flinching;
    }

    public void setFlinching(boolean flinching) {
        this.flinching = flinching;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public int getHealth() {
        return this.health;
    }
    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
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

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
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
            for (int j = 0; j < this.spells.size(); j++) {
                if(this.spells.get(j).intersects(e)){
                    e.hit(this.spells.get(j).getSpellDamage());
                    this.spells.get(j).setHit();
                    break;
                }
            }
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
        this.mana += 1;
        if(this.mana > this.maxMana) this.mana = this.maxMana;
        if(this.casting && this.getCurrentAction() != this.CASTING){
            if(this.mana > 200){
                this.mana -= 200;
                Spell temporarySpell = new Spell(this.getTileMap(), this.isFacingRight());
                temporarySpell.setPosition(this.getX(), this.getY());
                this.spells.add(temporarySpell);
            }
        }

        // update spells
       for (int i = 0; i < this.spells.size(); i++) {
           this.spells.get(i).update();
           if(this.spells.get(i).shouldRemove()){
               this.spells.remove(i);
               i--;
           }
       }

        // check done flinching
        if(this.flinching) {
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
                //this.setWidth(120);
            }
        }
        else if(this.casting) {
            if(this.getCurrentAction() != this.CASTING) {
                this.setCurrentAction(this.CASTING);
                this.getAnimation().setFrames(sprites.get(this.CASTING));
                this.getAnimation().setDelay(100);
                this.setWidth(120);
            }
        }
        else if(this.getDy() > 0) {
            if(this.gliding) {
                if(this.getCurrentAction() != this.GLIDING) {
                    this.setCurrentAction(this.GLIDING);
                    this.getAnimation().setFrames(sprites.get(this.GLIDING));
                    this.getAnimation().setDelay(100);
                    //this.setWidth(120);
                }
            }
            else if(this.getCurrentAction() != this.FALLING) {
                this.setCurrentAction(this.FALLING);
                this.getAnimation().setFrames(this.sprites.get(this.FALLING));
                this.getAnimation().setDelay(100);
                this.setWidth(120);
            }
        }
        else if(this.getDy() < 0) {
            if(this.getCurrentAction() != this.JUMPING) {
                this.setCurrentAction(this.JUMPING);
                this.getAnimation().setFrames(this.sprites.get(this.JUMPING));
                this.getAnimation().setDelay(-1);
                this.setWidth(120);
            }
        }
        else if(this.isLeft() || this.isRight()) {
            if(this.getCurrentAction() != this.WALKING) {
                this.setCurrentAction(this.WALKING);
                this.getAnimation().setFrames(this.sprites.get(this.WALKING));
                this.getAnimation().setDelay(40);
               // this.setWidth(120);
            }
        }
        else {
            if(this.getCurrentAction() != this.IDLE) {
                this.setCurrentAction(this.IDLE);
                this.getAnimation().setFrames(this.sprites.get(this.IDLE));
                this.getAnimation().setDelay(400);
                this.setWidth(120);
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
        for (int i = 0; i < this.spells.size(); i++) {
            this.spells.get(i).draw(g);
        }
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

