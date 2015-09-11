package Entity;

import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.*;

public abstract class MapObject {

    // tile stuff
    protected TileMap tileMap;
    protected int tileSize;
    protected double xmap;
    protected double ymap;

    // position and vector
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    // dimensions
    protected int width;
    protected int height;

    // collision box
    protected int cwidth;
    protected int cheight;

    // collision
    protected int currRow;
    protected int currCol;
    protected double xdest;
    protected double ydest;
    protected double xtemp;
    protected double ytemp;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    // animation
    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    protected boolean facingRight;

    // movement
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;

    // movement attributes
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;

    // constructor
    public MapObject(TileMap tm) {
        this.tileMap = tm;
        this.tileSize = tm.getTileSize();
    }

    public boolean intersects(MapObject o) {
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
    }

    public Rectangle getRectangle() {
        return new Rectangle(
                (int)this.x - this.cwidth,
                (int)this.y - this.cheight,
                this.cwidth,
                this.cheight
        );
    }

    public void calculateCorners(double x, double y) {

        int leftTile = (int)(x - this.cwidth / 2) / this.tileSize;
        int rightTile = (int)(x + this.cwidth / 2 - 1) / this.tileSize;
        int topTile = (int)(y - this.cheight / 2) / this.tileSize;
        int bottomTile = (int)(y + this.cheight / 2 - 1) / this.tileSize;

        int tl = this.tileMap.getType(topTile, leftTile);
        int tr = this.tileMap.getType(topTile, rightTile);
        int bl = this.tileMap.getType(bottomTile, leftTile);
        int br = this.tileMap.getType(bottomTile, rightTile);

        this.topLeft = tl == Tile.BLOCKED;
        this.topRight = tr == Tile.BLOCKED;
        this.bottomLeft = bl == Tile.BLOCKED;
        this.bottomRight = br == Tile.BLOCKED;

    }

    public void checkTileMapCollision() {

        this.currCol = (int)x / tileSize;
        this.currRow = (int)y / tileSize;

        this.xdest = this.x + this.dx;
        this.ydest = this.y + this.dy;

        this.xtemp = this.x;
        this.ytemp = this.y;

        calculateCorners(this.x, this.ydest);
        if(this.dy < 0) {
            if(this.topLeft || this.topRight) {
                this.dy = 0;
                this.ytemp = this.currRow * this.tileSize + this.cheight / 2;
            }
            else {
                this.ytemp += this.dy;
            }
        }
        if(this.dy > 0) {
            if(this.bottomLeft || this.bottomRight) {
                this.dy = 0;
                this.falling = false;
                this.ytemp = (this.currRow + 1) * this.tileSize - this.cheight / 2;
            }
            else {
                this.ytemp += this.dy;
            }
        }

        calculateCorners(this.xdest, this.y);
        if(this.dx < 0) {
            if(this.topLeft || this.bottomLeft) {
                this.dx = 0;
                this.xtemp = this.currCol * this.tileSize + this.cwidth / 2;
            }
            else {
                this.xtemp += this.dx;
            }
        }
        if(this.dx > 0) {
            if(this.topRight || this.bottomRight) {
                this.dx = 0;
                this.xtemp = (this.currCol + 1) * this.tileSize - this.cwidth / 2;
            }
            else {
                this.xtemp += this.dx;
            }
        }

        if(!this.falling) {
            calculateCorners(this.x, this.ydest + 1);
            if(!this.bottomLeft && !this.bottomRight) {
                this.falling = true;
            }
        }

    }

    public int getx() { return (int)this.x; }
    public int gety() { return (int)this.y; }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public int getCWidth() { return this.cwidth; }
    public int getCHeight() { return this.cheight; }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setMapPosition() {
        this.xmap = this.tileMap.getx();
        this.ymap = this.tileMap.gety();
    }

    public void setLeft(boolean b) { this.left = b; }
    public void setRight(boolean b) { this.right = b; }
    public void setUp(boolean b) { this.up = b; }
    public void setDown(boolean b) { this.down = b; }
    public void setJumping(boolean b) { this.jumping = b; }

    public boolean notOnScreen() {
        return this.x + this.xmap + this.width < 0 ||
                this.x + xmap - this.width > GamePanel.WIDTH ||
                this.y + ymap + this.height < 0 ||
                this.y + ymap - this.height > GamePanel.HEIGHT;
    }

    public void draw(Graphics2D g){
        if(this.facingRight) {
            g.drawImage(
                    this.animation.getImage(),
                    (int)(this.x + this.xmap - this.width / 2),
                    (int)(this.y + this.ymap - this.height / 2),
                    null
            );
        }
        else {
            g.drawImage(
                    this.animation.getImage(),
                    (int)(this.x + this.xmap - this.width / 2 + this.width),
                    (int)(this.y + this.ymap - this.height / 2),
                    -this.width,
                    this.height,
                    null
            );

        }
    }

}
