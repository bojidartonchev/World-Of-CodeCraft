package Entity;

import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Spell extends MapObject {

    private int damage;
    private int range;
    private int manaCost;
    private boolean hit;
    private boolean remove;
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;

    public Spell(TileMap tm, boolean right) {
        super(tm);

        this.setFacingRight(right);

        this.setMoveSpeed(3.8);
        if (right) {
            this.setDx(this.getMoveSpeed());
        } else {
            this.setDx(this.getDx() - this.getMoveSpeed());
        }

        this.setWidth(30);
        this.setHeight(30);
        this.setCwidth(14);
        this.setCheight(14);

        // load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
                            "/Sprites/Player/spell.gif"
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
            e.printStackTrace();
        }


    }


    public int getManaCost() {
        return this.manaCost;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getRange() {
        return this.range;
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

    public void draw(Graphics2D g){
        setMapPosition();

        super.draw(g);
    }
}
