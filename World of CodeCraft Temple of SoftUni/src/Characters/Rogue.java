package Characters;

public class Rogue extends Character{

    private int agility;
    private static final int DEFAULTROGUEAGILITY = 512; //TODO set agility

    public Rogue(String name) {
        super(name);
        this.setAgility(DEFAULTROGUEAGILITY);
    }

    public void setAgility(int value){
        if(value<0){
            throw new IllegalArgumentException("Agility cannot be negative");
        }
        this.agility = value;
    }
}
