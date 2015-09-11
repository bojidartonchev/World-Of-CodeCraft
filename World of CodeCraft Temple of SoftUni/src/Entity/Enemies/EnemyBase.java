package Entity.Enemies;

import Entity.MapObject;
import TileMap.TileMap;

public abstract class EnemyBase extends MapObject {

    protected int currentHealth;
    protected int maxHealth;
    protected boolean isDead;
    protected int damage;

    protected boolean isFlinching;
    protected long flinchTimer;

    protected EnemyBase(TileMap tileMap ){
        super(tileMap);
    }

    public boolean isDead(){
        return  this.isDead;
    }

    public int getDamage(){
        return this.damage;
    }

    public void hit(int damage){
        if(isDead || isFlinching){
            return;
        }
        this.currentHealth-=damage;

        if(this.currentHealth <= 0){
            this.currentHealth = 0;
            this.isDead = true;
        }
        this.isFlinching = true;
        this.flinchTimer = System.nanoTime();
    }
}
