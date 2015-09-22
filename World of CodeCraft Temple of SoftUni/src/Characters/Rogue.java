package Characters;

import TileMap.TileMap;

public class Rogue extends Character{

    private int agility;
    private static final int DEFAULT_ROGUE_AGILITY = 512; //TODO set agility
    private static final int DEFAULT_ROGUE_HEALTH = 6;

    public Rogue(String name, TileMap tileMap, int maxHealth) {
        super(name, tileMap, maxHealth);
        this.setAgility(this.DEFAULT_ROGUE_AGILITY);
    }
    public Rogue(String name, TileMap tileMap, int maxHealth,int level) {
        super(name, tileMap, maxHealth,level);
        this.setAgility(this.DEFAULT_ROGUE_AGILITY);
    }

    public void setAgility(int value){
        if(value < 0){
            throw new IllegalArgumentException("Agility cannot be negative");
        }
        this.agility = value;
    }
}
