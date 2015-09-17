package Entity;

import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.*;

public abstract class MapObject {

    // tile stuff
    private TileMap tileMap;
    private int tileSize;
    private double xmap;
    private double ymap;

    // position and vector
    private double x;
    private double y;
    private double dx;
    private double dy;

    // dimensions
    private int width;
    private int height;

    // collision box
    private int cwidth;
    private int cheight;

    // collision
    private int currRow;
    private int currCol;
    private double xdest;
    private double ydest;
    private double xtemp;
    private double ytemp;
    private boolean topLeft;
    private boolean topRight;
    private boolean bottomLeft;
    private boolean bottomRight;

    // animation
    private Animation animation;
    private int currentAction;
    private int previousAction;
    private boolean facingRight;

    // movement
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean jumping;
    private boolean falling;

    // movement attributes
    private double moveSpeed;
    private double maxSpeed;
    private double stopSpeed;
    private double fallSpeed;
    private double maxFallSpeed;
    private double jumpStart;
    private double stopJumpSpeed;

    // constructor
    public MapObject(TileMap tm) {
        this.tileMap = tm;
        this.tileSize = tm.getTileSize();
    }

    // Getters And Setters


    public int getCheight() {
        return cheight;
    }

    public void setCheight(int cheight) {
        this.cheight = cheight;
    }

    public TileMap getTileMap() {
        return this.tileMap;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public double getXmap() {
        return this.xmap;
    }

    public void setXmap(double xmap) {
        this.xmap = xmap;
    }

    public double getYmap() {
        return this.ymap;
    }

    public void setYmap(double ymap) {
        this.ymap = ymap;
    }

    public double getX() {
        return (int)this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return (int)this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDx() {
        return this.dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return this.dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCwidth() {
        return this.cwidth;
    }

    public void setCwidth(int cwidth) {
        this.cwidth = cwidth;
    }

    public double getXdest() {
        return this.xdest;
    }

    public void setXdest(double xdest) {
        this.xdest = xdest;
    }

    public double getYdest() {
        return this.ydest;
    }

    public void setYdest(double ydest) {
        this.ydest = ydest;
    }

    public double getXtemp() {
        return this.xtemp;
    }

    public void setXtemp(double xtemp) {
        this.xtemp = xtemp;
    }

    public double getYtemp() {
        return this.ytemp;
    }

    public void setYtemp(double ytemp) {
        this.ytemp = ytemp;
    }

    public int getCurrCol() {
        return this.currCol;
    }

    public void setCurrCol(int currCol) {
        this.currCol = currCol;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public int getCurrentAction() {
        return this.currentAction;
    }

    public void setCurrentAction(int currentAction) {
        this.currentAction = currentAction;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public double getMoveSpeed() {
        return this.moveSpeed;
    }

    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getStopSpeed() {
        return this.stopSpeed;
    }

    public void setStopSpeed(double stopSpeed) {
        this.stopSpeed = stopSpeed;
    }

    public double getFallSpeed() {
        return this.fallSpeed;
    }

    public void setFallSpeed(double fallSpeed) {
        this.fallSpeed = fallSpeed;
    }

    public double getMaxFallSpeed() {
        return this.maxFallSpeed;
    }

    public void setMaxFallSpeed(double maxFallSpeed) {
        this.maxFallSpeed = maxFallSpeed;
    }

    public double getJumpStart() {
        return this.jumpStart;
    }

    public void setJumpStart(double jumpStart) {
        this.jumpStart = jumpStart;
    }

    public double getStopJumpSpeed() {
        return this.stopJumpSpeed;
    }

    public void setStopJumpSpeed(double stopJumpSpeed) {
        this.stopJumpSpeed = stopJumpSpeed;
    }

    public Rectangle getRectangle() {
        return new Rectangle(
                (int)this.x - this.cwidth,
                (int)this.y - this.cheight,
                this.cwidth,
                this.cheight
        );
    }

    public boolean intersects(MapObject o) {
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
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
