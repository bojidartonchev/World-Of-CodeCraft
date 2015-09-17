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

        this.setMoveSpeed(0.3);
        this.setMaxSpeed(0.3);
        this.setFallSpeed(0.2);
        this.setMaxFallSpeed(10.0);

        this.setMaxHealth(maxHealth);
        this.setCurrentHealth(maxHealth);

        this.setDamage(damage);

        // sprite dimentions
        this.setWidth(30);
        this.setHeight(30);
        this.setCwidth(20);
        this.setCheight(20);

        super.loadSprites(spritesPath, numberOfSprites);
        this.setAnimation(animationDelay);
    }

    protected void setAnimation(long animationDelay) {
        super.setAnimation(animationDelay);

        this.setRight(true);
        this.setFacingRight(true);
    }
    
}
