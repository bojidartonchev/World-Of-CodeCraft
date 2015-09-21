package Entity;

import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Spell extends MapObject {

    private boolean hit;
    private boolean remove;
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;
    private int spellCost;
    private int spellDamage;

    public Spell(TileMap tm, boolean right) {
        super(tm);
        this.setSpellCost(200);
        this.setSpellDamage(5);
        this.setFacingRight(right);

        this.setMoveSpeed(3.8);
        if (right) {
            this.setDx(this.getMoveSpeed());
        } else {
            this.setDx(this.getDx() - this.getMoveSpeed());
        }

        this.setWidth(60);
        this.setHeight(60);
        this.setCwidth(34);
        this.setCheight(34);

        // load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
                            "/Sprites/Spells/magespell.gif"
                    )
            );

            this.sprites = new BufferedImage[4];
            for (int i = 0; i < this.sprites.length; i++) {
                this.sprites[i] = spritesheet.getSubimage(
                        i * this.getWidth(),
                        0,
                        this.getWidth(),
                        this.getHeight()
                );
            }

            this.hitSprites = new BufferedImage[3];
            for (int i = 0; i < this.hitSprites.length; i++) {
                this.hitSprites[i] = spritesheet.getSubimage(
                        i * this.getWidth(),
                        this.getHeight(),
                        this.getWidth(),
                        this.getHeight()
                );

                this.setAnimation(new Animation());
                this.getAnimation().setFrames(this.sprites);
                this.getAnimation().setDelay(70);
            }
        } catch (Exception e) {
            System.out.println("error loading image for spell");
        }


    }


    public void setHit() {
        if(this.hit) return;
        this.hit = true;
        this.getAnimation().setFrames(this.hitSprites);
        this.getAnimation().setDelay(70);
        this.setDx(0);
    }

    public boolean shouldRemove(){ return this.remove; }

    public void update(){
        checkTileMapCollision();
        setPosition(this.getXtemp(), this.getYtemp());

        // ako se udari no vse oshte hit ne e true go pravim true
        if(this.getDx() == 0 && !this.hit){
            setHit();
        }

        this.getAnimation().update();
        if(this.hit && this.getAnimation().hasPlayedOnce()){
            this.remove = true;
        }
    }

    public int getSpellDamage() {
        return spellDamage;
    }

    public void setSpellDamage(int spellDamage) {
        this.spellDamage = spellDamage;
    }

    public int getSpellCost() {
        return spellCost;
    }

    public void setSpellCost(int spellCost) {
        this.spellCost = spellCost;
    }

    public void draw(Graphics2D g){
        setMapPosition();

        super.draw(g);
    }
}
