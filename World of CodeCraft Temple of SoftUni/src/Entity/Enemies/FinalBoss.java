package Entity.Enemies;

import TileMap.TileMap;

public class FinalBoss extends Enemy{

    private final int numberOfSprites = 4;
    private  long animationDelay = 400;

    private final int maxHealth = 50;
    private final int damage = 15;

    private final String spritesPath = "/Sprites/Enemies/boss.gif";


    public FinalBoss(TileMap tileMap) {
        super(tileMap, EnemyType.BOSS);

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


        super.loadSprites(spritesPath, numberOfSprites);
        setAnimation(animationDelay);
    }

    protected void setAnimation(long animationDelay) {
        super.setAnimation(animationDelay);

        this.setRight(true);
        this.setFacingRight(true);
    }



}
