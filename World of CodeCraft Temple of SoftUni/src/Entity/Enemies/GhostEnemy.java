package Entity.Enemies;

import TileMap.TileMap;


public class GhostEnemy extends  Enemy{

    private final int numberOfSprites = 5;
    private final long animationDelay = 550;

    private final int maxHealth = 2;
    private final int damage = 1;

    private final String spritesPath = "/Sprites/Enemies/ghost-enemy.gif";

    public GhostEnemy(TileMap tileMap){
        super(tileMap, EnemyType.GHOST_ENEMY);

        this.setMoveSpeed(1.2);
        this.setMaxSpeed(1.2);
        this.setFallSpeed(0.0);
        this.setMaxFallSpeed(10.0);

        this.setMaxHealth(this.maxHealth);
        this.setCurrentHealth(this.maxHealth);

        this.setDamage(damage);

        // sprite dimentions
        this.setWidth(90);
        this.setHeight(90);
        this.setCwidth(50);
        this.setCheight(60);

        super.loadSprites(spritesPath, numberOfSprites);
        setAnimation(animationDelay);
    }

    protected void setAnimation(long animationDelay) {
        super.setAnimation(animationDelay);

        this.setRight(true);
        this.setFacingRight(true);
    }
}
