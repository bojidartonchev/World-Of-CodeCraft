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
    private int level;

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




    protected Character(String name, TileMap tileMap, int maxHealth, ArrayList<Spell> spells, Spell spell){
        super(tileMap);
        this.name = name;
        this.health = this.maxHealth = maxHealth;
        this.spells = spells;
        this.spell = spell;
    }

    private void init(){
        this.width = 30;
        this.height = 30;
        this.cwidth = 20;
        this.cheight = 20;
        this.facingRight = true;

        // load sprites // vse oshte nqmame gif za characterite
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Player/playersprites.gif"
                    )
            );

            sprites = new ArrayList<BufferedImage[]>();
            for(int i = 0; i < 7; i++) {

                BufferedImage[] bi =
                        new BufferedImage[this.numFrames[i]];

                for(int j = 0; j < this.numFrames[i]; j++) {

                    if(i != this.ATTACKING) {
                        bi[j] = spritesheet.getSubimage(
                                j * this.width,
                                i * this.height,
                                this.width,
                                this.height
                        );
                    }
                    else {
                        bi[j] = spritesheet.getSubimage(
                                j * this.width * 2,
                                i * this.height,
                                this.width * 2,
                                this.height
                        );
                    }

                }

                this.sprites.add(bi);

            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        this.animation = new Animation();
        this.currentAction = this.IDLE;
        this.animation.setFrames(this.sprites.get(this.IDLE));
        this.animation.setDelay(400);
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
    public int getLevel() {
        return this.level;
    }
    public String getName() {
        return this.name;
    }


    public void setLevel(int value) {
        this.level = value;
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
        // loop through enemies
        for (int i = 0; i < enemies.size(); i++) {

            Enemy e = enemies.get(i);

            // scratch attack
            if (this.attacking) {
                if (this.facingRight) {

                    if (
                            e.getx() > this.x &&
                                    e.getx() <= this.x + this.attackRange &&
                                    e.gety() > this.y - this.height / 2 &&
                                    e.gety() < this.y + this.height / 2
                            ) {
                        e.hit(this.attackDamage);
                    }
                }else {
                    if(
                            e.getx() < this.x &&
                                    e.getx() > this.x - this.attackRange &&
                                    e.gety() > this.y - this.height / 2 &&
                                    e.gety() < this.y + this.height / 2
                            ){
                        e.hit(this.attackDamage);
                    }
                }
            }
            // spells
            for (int j = 0; j < this.spells.size(); j++) {
                if(this.spells.get(j).intersects(e)){
                    e.hit(this.spell.getDamage());
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
        if(this.left) {
            this.dx -= this.moveSpeed;
            if(this.dx < -this.maxSpeed) {
                this.dx = -this.maxSpeed;
            }
        }
        else if(this.right) {
            this.dx += this.moveSpeed;
            if(this.dx > this.maxSpeed) {
                this.dx = this.maxSpeed;
            }
        }
        else {
            if(this.dx > 0) {
                this.dx -= this.stopSpeed;
                if(this.dx < 0) {
                    this.dx = 0;
                }
            }
            else if(this.dx < 0) {
                this.dx += this.stopSpeed;
                if(this.dx > 0) {
                    this.dx = 0;
                }
            }
        }

        // cannot move while attacking, except in air
        if(
                (this.currentAction == this.ATTACKING || this.currentAction == this.CASTING) &&
                        !(this.jumping || this.falling)) {
            this.dx = 0;
        }

        // jumping
        if(this.jumping && !this.falling) {
            this.dy = this.jumpStart;
            this.falling = true;
        }

        // falling
        if(this.falling) {

            if(this.dy > 0 && this.gliding) this.dy += this.fallSpeed * 0.1;
            else this.dy += this.fallSpeed;

            if(this.dy > 0) this.jumping = false;
            if(this.dy < 0 && !this.jumping) this.dy += this.stopJumpSpeed;

            if(this.dy > this.maxFallSpeed) this.dy = this.maxFallSpeed;

        }

    }

    public void update() {

        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(this.xtemp, this.ytemp);

        // check attack has stopped
        if(this.currentAction == this.ATTACKING){
            if(this.animation.hasPlayedOnce()) this.attacking = false;
        }if(this.currentAction == this.CASTING){
            if(this.animation.hasPlayedOnce()) this.casting = false;
        }

        // spell attack
        this.mana += 10;
        if(this.mana > this.maxMana) this.mana = this.maxMana;
        if(this.casting && this.currentAction != this.CASTING){
            if(this.mana > this.spell.getManaCost()){
                this.mana -= this.spell.getManaCost();
                Spell temporarySpell = new Spell(this.tileMap, this.facingRight);
                temporarySpell.setPosition(this.x, this.y);
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
        if(this.flinching){
            long elapsed = (System.nanoTime() - this.flinchTimer) / 1000000;
            if(elapsed > 1000){
                this.flinching = false;
            }
        }

        // set animation
        if(this.attacking) {
            if(this.currentAction != this.ATTACKING) {
                this.currentAction = this.ATTACKING;
                this.animation.setFrames(this.sprites.get(this.ATTACKING));
                this.animation.setDelay(50);
                this.width = 60;
            }
        }
        else if(this.casting) {
            if(this.currentAction != this.CASTING) {
                this.currentAction = this.CASTING;
                this.animation.setFrames(sprites.get(this.CASTING));
                this.animation.setDelay(100);
                this.width = 30;
            }
        }
        else if(this.dy > 0) {
            if(this.gliding) {
                if(this.currentAction != this.GLIDING) {
                    this.currentAction = this.GLIDING;
                    this.animation.setFrames(sprites.get(this.GLIDING));
                    this.animation.setDelay(100);
                    this.width = 30;
                }
            }
            else if(this.currentAction != this.FALLING) {
                this.currentAction = this.FALLING;
                this.animation.setFrames(this.sprites.get(this.FALLING));
                this.animation.setDelay(100);
                this.width = 30;
            }
        }
        else if(this.dy < 0) {
            if(this.currentAction != this.JUMPING) {
                this.currentAction = this.JUMPING;
                this.animation.setFrames(this.sprites.get(this.JUMPING));
                this.animation.setDelay(-1);
                this.width = 30;
            }
        }
        else if(this.left || this.right) {
            if(this.currentAction != this.WALKING) {
                this.currentAction = this.WALKING;
                this.animation.setFrames(this.sprites.get(this.WALKING));
                this.animation.setDelay(40);
                this.width = 30;
            }
        }
        else {
            if(this.currentAction != this.IDLE) {
                this.currentAction = this.IDLE;
                this.animation.setFrames(this.sprites.get(this.IDLE));
                this.animation.setDelay(400);
                this.width = 30;
            }
        }

        this.animation.update();

        // set direction
        if(this.currentAction != this.ATTACKING && this.currentAction != this.CASTING) {
            if(this.right) this.facingRight = true;
            if(this.left) this.facingRight = false;
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

