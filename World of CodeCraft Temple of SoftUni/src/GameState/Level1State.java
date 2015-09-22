package GameState;

import Characters.Character;
import Entity.Enemies.*;
import Entity.HUD;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Level1State extends GameState{

    private TileMap tileMap;
    private Background bg;
    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;
    private Character player;
    private boolean hasWon = false;

    private HUD hud;
    private boolean isBossDropped = false;

    public Level1State(GameStateManager gsm) {
        super(gsm);
        this.tileMap = new TileMap(120);
        this.tileMap.loadTiles("/Tilesets/grasstileset.gif");
        this.tileMap.loadMap("/Maps/level1-1.map");
        this.tileMap.setPosition(0, 0);
        this.bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
        this.explosions = new ArrayList<>();
        super.setTileMap(this.tileMap);
        initEnemies();
    }
    @Override
    public void initialize() {


        this.player = this.getCharacter();
        this.hud = new HUD(this.player);

        this.player.setPosition(200, 600);


    }

    private void initEnemies() {
        this.enemies = new ArrayList<Enemy>();
        Point[] helmetCoordinates = new Point[]{
          new Point(700, 800),
          new Point(3000, 800),
          new Point(3900, 800),
          new Point(5900, 800),
          new Point(6800, 800),
          new Point(7000, 800),

        };

        Enemy helemetEnemy ;

        for (int i = 0; i < helmetCoordinates.length; i++) {
            helemetEnemy = new HelmetEnemy(this.getTileMap());
            helemetEnemy.setPosition(helmetCoordinates[i].x, helmetCoordinates[i].y);
            this.enemies.add(helemetEnemy);
        }

        Point[] ghostsCoordinates = new Point[]{
                new Point(3000, 400),
                new Point(3900, 400),
                new Point(5900, 400),
                new Point(6800, 580),
                new Point(10500, 500),
                new Point(10800, 600),
                new Point(12000, 600),
                new Point(12500, 510),



        };

        GhostEnemy ghostEnemy;
        for (int i = 0; i < ghostsCoordinates.length; i++) {
            ghostEnemy = new GhostEnemy(this.getTileMap());
            ghostEnemy.setPosition(ghostsCoordinates[i].x, ghostsCoordinates[i].y);
            this.enemies.add(ghostEnemy);
        }

    }


    @Override
    public void update() {

        // update player
        try{
        if(player.getHealth() <= 0 || this.player.isPlayerOutOfBoundsOfMap()){
            this.bg = new Background("/Backgrounds/gameover.png",0);
            return;
        }}catch (Exception ex){
            System.out.println("Game over!!!");
        }
        try{
            if(this.hasWon == true){
                this.bg = new Background("/Backgrounds/Winner.png",0);
                return;
            }}catch (Exception ex){
            System.out.println("You Won!!!");
        }

        if(!isBossDropped){
            FinalBoss boss = new FinalBoss(this.getTileMap(), this.player);
            boss.setPosition(400,800);
            this.enemies.add(boss);
            this.isBossDropped = true;
        }
        this.player.update();

        this.tileMap.setPosition(
                GamePanel.WIDTH / 2 - this.player.getX(),
                GamePanel.HEIGHT / 2 - this.player.getY()
        );

        // update background
        this.bg.setPosition(this.tileMap.getx(), this.tileMap.gety());

        // attack enemies

        player.checkAttack(this.enemies);

        // update all enemies
        for (int i = 0; i < this.enemies.size(); i++) {
            Enemy enemy = this.enemies.get(i);
            enemy.update();
            if(enemy.isDead()){
                if(enemy.getType() == EnemyType.BOSS){
                    this.hasWon = true;

                }
                this.enemies.remove(enemy);
                i--;
                explosions.add(new Explosion((int)enemy.getX(), (int)enemy.getY()));

            }
        }

        //// update explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if(explosions.get(i).shouldRemove()){
                explosions.remove(i);
                i--;
            }
        }


    }

    @Override
    public void draw(Graphics2D g) {



       // GamePanel.SCALE = 3;

        // draw bg
        this.bg.draw(g);

        // draw tilemap
        this.tileMap.draw(g);

        // draw player
        this.player.draw(g);

        // draw enemies
        for (int i = 0; i < this.enemies.size(); i++) {
            this.enemies.get(i).draw(g);
        }

        //draw explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).setMapPosotion((int)tileMap.getx(), (int)tileMap.gety());
            explosions.get(i).draw(g);
        }
//
        //// draw HUD
        this.hud.draw(g);
    }

    public void keyPressed(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_W) player.setJumping(true);
        if(k == KeyEvent.VK_E) player.setGliding(true);
        if(k == KeyEvent.VK_R) player.setAttacking();
        if(k == KeyEvent.VK_F) player.setCasting();
    }

    public void keyReleased(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_W) player.setJumping(false);
        if(k == KeyEvent.VK_E) player.setGliding(false);
    }

}