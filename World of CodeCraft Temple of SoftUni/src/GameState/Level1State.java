package GameState;
import Main.GamePanel;
import TileMap.*;
import java.awt.*;

public class Level1State extends GameState{

    private TileMap tileMap;

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
    }

    @Override
    public void draw(Graphics2D g) {
        // clear screen

        GamePanel.SCALE = 3;
        GamePanel.HEIGHT = 820;

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        //draw tilemap
        this.tileMap.draw(g);

    }

    @Override
    public void update() {

    }

    @Override
    public void keyPressed(int k) {

    }

    @Override
    public void keyReleased(int k) {

    }
}
