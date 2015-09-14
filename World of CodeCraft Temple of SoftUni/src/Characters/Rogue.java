package Characters;

import Entity.Spell;
import TileMap.TileMap;

import java.util.ArrayList;

public class Rogue extends Character{

    private int agility;
    private static final int DEFAULTROGUEAGILITY = 512; //TODO set agility
    private static final int DEFAULTROGUEHEALTH = 6;

    public Rogue(String name, TileMap tileMap, int maxHealth, ArrayList<Spell> spells, Spell spell) {
        super(name, tileMap, maxHealth, spells, spell);
        this.setAgility(DEFAULTROGUEAGILITY);
    }

    public void setAgility(int value){
        if(value < 0){
            throw new IllegalArgumentException("Agility cannot be negative");
        }
        this.agility = value;
    }
}
