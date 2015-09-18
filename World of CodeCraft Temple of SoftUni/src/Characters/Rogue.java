package Characters;

import Entity.Spell;
import TileMap.TileMap;

import java.util.ArrayList;

public class Rogue extends Character{

    private int agility;
    private static final int DEFAULTROGUEAGILITY = 512; //TODO set agility
    private static final int DEFAULTROGUEHEALTH = 6;

    public Rogue(String name, TileMap tileMap, int maxHealth) {
        super(name, tileMap, maxHealth);
        this.setAgility(this.DEFAULTROGUEAGILITY);
    }
    public Rogue(String name, TileMap tileMap, int maxHealth,int level) {
        super(name, tileMap, maxHealth,level);
        this.setAgility(this.DEFAULTROGUEAGILITY);
    }

    public void setAgility(int value){
        if(value < 0){
            throw new IllegalArgumentException("Agility cannot be negative");
        }
        this.agility = value;
    }
}
