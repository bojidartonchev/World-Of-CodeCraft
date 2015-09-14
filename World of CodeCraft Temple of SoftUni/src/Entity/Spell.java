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

        this.facingRight = right;

        this.moveSpeed = 3.8;
        if (right) {
            this.dx = this.moveSpeed;
        } else {
            this.dx = -this.moveSpeed;
        }

        this.width = 30;
        this.height = 30;
        this.cwidth = 14;
        this.cheight = 14;

        // load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
                            "/Sprites/Player/spell.gif"
                    )
            );

            this.sprites = new BufferedImage[4];
            for (int i = 0; i < this.sprites.length; i++) {
                this.sprites[i] = spritesheet.getSubimage(
                        i * this.width,
                        0,
                        this.width,
                        this.height
                );
            }

            this.hitSprites = new BufferedImage[3];
            for (int i = 0; i < this.hitSprites.length; i++) {
                this.hitSprites[i] = spritesheet.getSubimage(
                        i * this.width,
                        this.height,
                        this.width,
                        this.height
                );

                this.animation = new Animation();
                this.animation.setFrames(this.sprites);
                this.animation.setDelay(70);
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
        this.animation.setFrames(this.hitSprites);
        this.animation.setDelay(70);
        this.dx = 0;
    }

    public boolean shouldRemove(){ return this.remove; }

    public void update(){
        checkTileMapCollision();
        setPosition(this.xtemp, this.ytemp);

        // ako se udari no vse oshte hit ne e true go pravim true
        if(this.dx == 0 && !this.hit){
            setHit();
        }

        this.animation.update();
        if(this.hit && this.animation.hasPlayedOnce()){
            this.remove = true;
        }
    }

    public void draw(Graphics2D g){
        setMapPosition();

        super.draw(g);
    }
}
