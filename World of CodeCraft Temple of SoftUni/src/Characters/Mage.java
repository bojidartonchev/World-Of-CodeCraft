package Characters;

public class Mage extends Character {

    private int intellect;
    private static final int DEFAULTMAGEINTELLECT = 512; //TODO set intellect

    public Mage(String name) {
        super(name);
        this.setIntellect(DEFAULTMAGEINTELLECT);
    }

    public void setIntellect(int value){
        if(value<0){
            throw new IllegalArgumentException("Intellect cannot be negative");
        }
        this.intellect = value;
    }

}
