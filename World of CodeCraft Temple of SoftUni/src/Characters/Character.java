package Characters;

import Interfaces.ICharacter;

public abstract class Character implements ICharacter{

    private int health;
    private int level;
    private String name;

    protected Character(String name){
        this.name = name;
    }

    public int getHealth() {
        return this.health;
    }
    public void setHealth(int value) {
        this.health = value;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int value) {
        this.level = value;
    }

    public String getName() {
        return this.name;
    }

    public void hit(int damage){
        this.health-=damage;
    }
}
