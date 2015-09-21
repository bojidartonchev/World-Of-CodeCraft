package Entity.Enemies;

import Entity.Enemies.Enemy;import Entity.Enemies.EnemyType;import TileMap.TileMap;import java.lang.Override;import java.lang.String;

public class FinalBoss extends Enemy{
    private long counter = 0;

    private final int numberOfSprites = 4;
    private  long animationDelay = 400;

    private final int maxHealth = 30;
    private final int damage = 15;

    private final String spritesPath = "/Sprites/Enemies/boss.gif";
    private boolean animationSetOnce = false;

    public FinalBoss(TileMap tileMap) {
        super(tileMap, EnemyType.BOSS);

        this.setMoveSpeed(3.9);
        this.setMaxSpeed(3.9);
        this.setFallSpeed(0.5);
        this.setMaxFallSpeed(10.0);

        this.setMaxHealth(this.maxHealth);
        this.setCurrentHealth(this.maxHealth);

        this.setDamage(damage);

        // sprite dimentions
        this.setWidth(60);
        this.setHeight(60);
        this.setCwidth(45);
        this.setCheight(40);


        super.loadSprites(spritesPath, numberOfSprites);
        setAnimation(animationDelay);
    }

    protected void setAnimation(long animationDelay) {
        super.setAnimation(animationDelay);

        if(!animationSetOnce){
            this.setRight(true);
            this.setFacingRight(true);
            this.animationSetOnce = true;
        }
    }

    @Override
    protected void goToNextPosition() {

        this.counter++;
        // movement
        if(this.isLeft()){
            this.setDx(this.getDx() - this.getMoveSpeed());
            if(this.getDx() < -this.getMaxSpeed()){
                this.setDx(-this.getMaxSpeed()* 3 );
                this.animationDelay = 150;
            }
        }
        else if(this.isRight()){
            this.setDx(this.getDx() + this.getMoveSpeed());
            if(this.getDx() > this.getMaxSpeed()){
                this.setDx(this.getMaxSpeed());

            }
        }

        if(this.isFalling()) {
            this.setDy(this.getDy() + this.getFallSpeed());
        }

    }
}
