package Entity.Enemies;


import TileMap.TileMap;

public class HelmetEnemy extends Enemy {

    private final int maxHealth = 2;
    private final int damage = 2;

    private final int numberOfSprites = 8;
    private final long animationDelay = 200;
    private final String spritesPath = "/Sprites/Enemies/helmet-enemy.gif";

    public HelmetEnemy(TileMap tileMap){
        super(tileMap, EnemyType.HELMET_ENEMY);

        this.moveSpeed = 0.3;
        this.maxSpeed = 0.3;
        this.fallSpeed = 0.2;
        this.maxFallSpeed = 10.0;

        this.setMaxHealth(maxHealth);
        this.setCurrentHealth(maxHealth);

        this.setDamage(damage);

        // sprite dimentions
        this.width = 30;
        this.height = 30;
        this.cwidth = 20;
        this.cheight = 20;

        super.loadSprites(spritesPath, numberOfSprites);
        this.setAnimation(animationDelay);
    }

    protected void setAnimation(long animationDelay) {
        super.setAnimation(animationDelay);

        this.right = true;
        this.facingRight = true;
    }
    
}
