package GameState;
import Entity.Enemies.Enemy;
import Entity.Enemies.GhostEnemy;
import Entity.Enemies.HelmetEnemy;
import Main.GamePanel;
import TileMap.*;
import java.awt.*;
import java.util.ArrayList;


public class Level1State extends GameState{

    private TileMap tileMap;
    private Background bg;
    private ArrayList<Enemy> enemies;
    //private HUD hud;
    public Level1State(GameStateManager gsm) {
        super(gsm);
        initialize();
    }

    @Override
    public void initialize() {
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
    public void draw(Graphics2D g) {
        // clear screen

        GamePanel.SCALE = 3;
        GamePanel.HEIGHT = 820;

        bg.draw(g);

        //draw tilemap
        this.tileMap.draw(g);
       // this.hud.draw(g);
        this.enemies.stream().forEach(enemy -> enemy.draw(g));
    }

    @Override
    public void update() {
        // update enemies
       this.enemies.stream().forEach(enemy-> enemy.update());
    }

    @Override
    public void keyPressed(int k) {

    }

    @Override
    public void keyReleased(int k) {

    }

}
