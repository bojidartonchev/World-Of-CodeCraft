package Characters;

import TileMap.TileMap;

public class Mage extends Character {

    private int intellect;
    private static final int DEFAULT_MAGE_INTELLECT = 512; //TODO set intellect

    public Mage(String name, TileMap tileMap, int maxHealth) {
        super(name, tileMap, maxHealth);
        this.setIntellect(this.DEFAULT_MAGE_INTELLECT);
    }
    public Mage(String name, TileMap tileMap, int maxHealth,int level) {
        super(name, tileMap, maxHealth,level);
        this.setIntellect(this.DEFAULT_MAGE_INTELLECT);
    }

    public void setIntellect(int value){
        if(value < 0){
            throw new IllegalArgumentException("Intellect cannot be negative");
        }
        this.intellect = value;
    }

}
