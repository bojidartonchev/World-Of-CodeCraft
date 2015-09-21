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
    private boolean isInitialize = false;

    private HUD hud;
    public Level1State(GameStateManager gsm) {
        super(gsm);
        this.tileMap = new TileMap(120);
        this.tileMap.loadTiles("/Tilesets/grasstileset.gif");
        this.tileMap.loadMap("/Maps/level1-1.map");
        this.tileMap.setPosition(0, 0);
        this.bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
        super.setTileMap(this.tileMap);
        this.explosions = new ArrayList<>();
        initEnemies();

    }

    @Override
    public void initialize() {

        // TODO: Player is null
        this.player = this.getCharacter();
        this.hud = new HUD(this.player);

        this.player.setPosition(200, 600);


    }

    private void initEnemies() {
        this.enemies = new ArrayList<Enemy>();
        Enemy firstEnemy = new HelmetEnemy(this.tileMap);

        firstEnemy.setPosition(660, 800);
        this.enemies.add(firstEnemy);

        Enemy secondEnemy = new GhostEnemy(this.tileMap);
        secondEnemy.setPosition(200, 400);
        this.enemies.add(secondEnemy);

        Enemy boss = new FinalBoss(this.tileMap);
        boss.setPosition(600, 400);
        this.enemies.add(boss);
    }


    @Override
    public void update() {
        // update enemies
        this.enemies.stream().forEach(enemy-> enemy.update());

        // update player

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

        ////draw explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).setMapPosotion((int) tileMap.getx(), (int) tileMap.gety());
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