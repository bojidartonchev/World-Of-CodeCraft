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

        this.moveSpeed = 0.2;
        this.maxSpeed = 0.2;
        this.fallSpeed = 0.0;
        this.maxFallSpeed = 10.0;

        this.setMaxHealth(this.maxHealth);
        this.setCurrentHealth(this.maxHealth);

        this.setDamage(damage);

        // sprite dimentions
        this.width = 30;
        this.height = 30;
        this.cwidth = 20;
        this.cheight = 20;

        super.loadSprites(spritesPath, numberOfSprites);
        setAnimation(animationDelay);
    }

    protected void setAnimation(long animationDelay) {
        super.setAnimation(animationDelay);

        this.left = true;
        this.facingRight = false;
    }
}
