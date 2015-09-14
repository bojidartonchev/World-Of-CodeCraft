package Characters;

import Entity.Spell;
import TileMap.TileMap;

import java.util.ArrayList;

public class Mage extends Character {

    private int intellect;
    private static final int DEFAULTMAGEINTELLECT = 512; //TODO set intellect

    public Mage(String name, TileMap tileMap, int maxHealth, ArrayList<Spell> spells, Spell spell) {
        super(name, tileMap, maxHealth, spells, spell);
        this.setIntellect(this.DEFAULTMAGEINTELLECT);
    }

    public void setIntellect(int value){
        if(value < 0){
            throw new IllegalArgumentException("Intellect cannot be negative");
        }
        this.intellect = value;
    }

}
