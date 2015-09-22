package Entity.Enemies;

import Characters.*;
import Characters.Character;
import TileMap.TileMap;

import java.awt.*;

public class FinalBoss extends Enemy{

    private static final int IDLE = 0;
    private static final int JUMPING = 1;
    private static final int ATTACKING = 2;

    private final int numberOfSprites = 4;
    private  long animationDelay = 400;

    private final int maxHealth = 50;
    private final int damage = 15;

    private boolean jumping;

    private int attackTick;
    private int attackDelay = 30;
    private int step;

    private long flinchCount;

    private final String spritesPath = "/Sprites/Enemies/boss.gif";

    private Character player;


    public FinalBoss(TileMap tileMap, Character player) {
        super(tileMap, EnemyType.BOSS);

        this.player = player;
        this.setMoveSpeed(4.5);
        this.setMaxSpeed(6);
        this.setFallSpeed(0.5);
        this.setMaxFallSpeed(10.0);

        this.setMaxHealth(this.maxHealth);
        this.setCurrentHealth(this.maxHealth);

        this.setDamage(damage);

        // sprite dimentions
        this.setWidth(180);
        this.setHeight(180);
        this.setCwidth(145);
        this.setCheight(140);


        setFallSpeed(0.15);
        setMaxFallSpeed(4.0);
        setJumpStart(-5);
//
        super.loadSprites(spritesPath, numberOfSprites);
        setAnimation(animationDelay);
    }

    @Override
    protected void goToNextPosition(){
        if(isLeft()) setDx(-getMoveSpeed());
        else if(isRight()) setDx(getMoveSpeed());
        else setDx(0);
        if(isFalling()) {
            setDy(getDy() + getFallSpeed());
            if(getDy() > getMaxFallSpeed()) setDy(getMaxFallSpeed());
        }
        if(jumping && !isFalling()) {
            setDy(getJumpStart());
        }

    }

    @Override
    public void update(){
        // check if done flinching
        if(isFlinching()) {
            flinchCount++;
            if(flinchCount == 6) setFlinching(false);
        }

        goToNextPosition();
        checkTileMapCollision();
        setPosition(getXtemp(), getYtemp());

        // update animation
        this.getAnimation().update();

        if((player.getX() < getX())) {
            setFacingRight(false);
        }
        else setFacingRight(true);

        // idle
        if(step == 0) {
            if(getCurrentAction() != IDLE) {
                setCurrentAction(IDLE);
//                getAnimation().setFrames(idleSprites);
//                animation.setDelay(-1);
            }
            attackTick++;
            if(attackTick >= attackDelay && Math.abs(player.getX() - getX()) < 60) {
                step++;
                attackTick = 0;
            }
        }
        // jump away
        if(step == 1) {
            if(getCurrentAction() != JUMPING) {
                setCurrentAction(JUMPING);
               // getAnimation().setFrames(jumpSprites);
                getAnimation().setDelay(-1);
            }
            jumping = true;
            if(isFacingRight()) setLeft(true);
            else setRight(true);
            if(isFalling()) {
                step++;
            }
        }
        // attack
        if(step == 2) {
            if(getDy() > 0 && getCurrentAction() != ATTACKING) {
                setCurrentAction(ATTACKING);
              //  getAnimation().setFrames(attackSprites);
                getAnimation().setDelay(3);
                //DarkEnergy de = new DarkEnergy(tileMap);
                //de.setPosition(x, y);
//                if(facingRight) de.setVector(3, 3);
//                else de.setVector(-3, 3);
//                enemies.add(de);
            }
            if(getCurrentAction() == ATTACKING && getAnimation().hasPlayedOnce()) {
                step++;
                setCurrentAction(JUMPING);
               // getAnimation().setFrames(jumpSprites);
                getAnimation().setDelay(-1);
            }
        }
        // done attacking
        if(step == 3) {
            if(getDy() == 0) step++;
        }
        // land
        if(step == 4) {
            step = 0;
            setLeft(false);
            setRight(false);
            setJumping(false);
        }

    }
    protected void setAnimation(long animationDelay) {
        super.setAnimation(animationDelay);

        this.setRight(true);
        this.setFacingRight(true);
    }

    @Override
    public void draw(Graphics2D graphics2D){
        if(isFlinching()) {
            if(flinchCount == 0 || flinchCount == 2) return;
        }
        super.draw(graphics2D);
    }

}
