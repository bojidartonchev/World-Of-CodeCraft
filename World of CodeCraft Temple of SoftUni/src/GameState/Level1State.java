package GameState;
import Characters.*;
import Characters.Character;
import Entity.Enemies.Enemy;
import Entity.Enemies.GhostEnemy;
import Entity.Enemies.HelmetEnemy;
import Main.Game;
import Main.GamePanel;
import TileMap.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Level1State extends GameState{

    private TileMap tileMap;
    private Background bg;
    private ArrayList<Enemy> enemies;
    private Character player;
    private boolean isInitialize = false;

    //private HUD hud;
    public Level1State(GameStateManager gsm) {
        super(gsm);
        initialize();

    }

    @Override
    public void initialize() {

        this.player = this.gsm.gameStates.get(GameStateManager.LOAD_CHARACTER_STATE).getCharacter();

        this.tileMap = new TileMap(30);
        this.tileMap.loadTiles("/Tilesets/grasstileset.gif");
        this.tileMap.loadMap("/Maps/level1-1.map");
        this.tileMap.setPosition(0, 0);
        this.bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
        super.setTileMap(this.tileMap);
        initEnemies();


    }

    private void initEnemies() {
        this.enemies = new ArrayList<Enemy>();
        Enemy firstEnemy = new HelmetEnemy(tileMap);
        firstEnemy.setPosition(250, 200);
        this.enemies.add(firstEnemy);

        Enemy secondEnemy = new GhostEnemy(tileMap);
        secondEnemy.setPosition(200, 100);
        this.enemies.add(secondEnemy);
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
                // explosions.add(new Explosion(enemy.getx(), enemy.gety()));

            }
        }

        //// update explosions
        //for (int i = 0; i < explosions.size(); i++) {
        //    explosions.get(i).update();
        //    if(explosions.get(i).shouldRemove()){
        //        explosions.remove(i);
        //        i--;
        //    }
        //}

        // update enemies
        //this.enemies.stream().forEach(enemy-> enemy.update());
    }

    @Override
    public void draw(Graphics2D g) {

        if(!isInitialize){
            this.player.setPosition(200, 200);
            isInitialize = true;
        }

        GamePanel.SCALE = 3;

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
        //for (int i = 0; i < explosions.size(); i++) {
        //    explosions.get(i).setMapPosotion((int)tileMap.getx(), (int)tileMap.gety());
        //    explosions.get(i).draw(g);
        //}
//
        //// draw HUD
        //hud.draw(g);
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
