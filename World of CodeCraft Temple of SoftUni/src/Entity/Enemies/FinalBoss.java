package Entity.Enemies;

import Characters.*;
import Characters.Character;
import Entity.Spell;
import TileMap.TileMap;

import java.awt.*;

public class FinalBoss extends Enemy{

    private final int numberOfSprites = 4;
    private  long animationDelay = 300;

    private final int maxHealth = 350;
    private final int damage = 19;

    private boolean jumping;
    private long flinchCount;

    private final String spritesPath = "/Sprites/Enemies/boss.gif";

    private Character player;
    private Spell spell;
    private final int  bossStartFightPosition = 10200;


    public FinalBoss(TileMap tileMap, Character player) {
        super(tileMap, EnemyType.BOSS);

        initFinalBoss(player);

        spell = new Spell(getTileMap(), isFacingRight());

        super.loadSprites(spritesPath, numberOfSprites);
        setAnimation(animationDelay);
    }

    private void initFinalBoss(Character player) {
        this.player = player;
        this.setMoveSpeed(2.9);
        this.setMaxSpeed(2.9);
        this.setFallSpeed(0.7);
        this.setMaxFallSpeed(10.0);

        this.setJumpStart(-5);

        this.setCurrentHealth(this.maxHealth);
        this.setMaxHealth(this.maxHealth);

        this.setDamage(damage);

        // sprite dimentions
        this.setWidth(180);
        this.setHeight(180);
        this.setCwidth(145);
        this.setCheight(140);


    }

    @Override
    protected void goToNextPosition(){
        if(isLeft()) setDx(-getMoveSpeed());
        else if(isRight()) setDx(getMoveSpeed());
        //else setDx(0);
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
         setFlinching(false);
        }

        goToNextPosition();

        checkTileMapCollision();

        setPosition(getXtemp(), getYtemp());

        chasePlayer();

        getAnimation().update();
    }

    private void chasePlayer() {
        if(this.player.getX() >= bossStartFightPosition) {
            // bool
            if((this.player.getX() < getX())) {
                setLeft(true);
                setRight(false);
                setFacingRight(false);

                setJumping(true);
                this.jumping = true;
            }
            else if((this.player.getX() == getX())){

                this.jumping = false;
                setJumping(false);

                setJumping(true);
                this.jumping = true;

            }
            else {
                setLeft(false);
                setRight(true);
                setFacingRight(true);
            }

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
            if(flinchCount == 0 || flinchCount == 2)
                return;
        }
        super.draw(graphics2D);
    }

}
