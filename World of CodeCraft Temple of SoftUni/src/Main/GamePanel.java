package Main;

import GameState.GameStateManager;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

//Proveri KeyListener V Neta
public class GamePanel extends JPanel implements Runnable, KeyListener {
    //dimensions
    public static int WIDTH = 1365;
    public static int HEIGHT = 768;
    public static int SCALE = 1;

    //game thread

    private Thread thread;
    private boolean isRunning;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;

    //image
    private BufferedImage image;
    private Graphics2D g; //Proveri Graphics2D V Net;

    //game state manager
    private GameStateManager gsm;


    public GamePanel() {
        super();
        setPreferredSize(new Dimension(this.WIDTH * this.SCALE, this.HEIGHT * this.SCALE));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if (this.thread == null) {
            this.thread = new Thread(this);
            addKeyListener(this);
            this.thread.start();
        }
    }

    //initialize
    private void initialize() {
        //Proveri BufferedImage V Neta
        this.image = new BufferedImage(this.WIDTH, this.HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.g = (Graphics2D) this.image.getGraphics();
        this.isRunning = true;

        this.gsm = new GameStateManager(this);
    }

    public void run() {
        initialize();

        long start;
        long elapsed;
        long wait;

        while (isRunning) {
            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = this.targetTime - elapsed / 1000000;
            if(wait < 0) wait = 5;

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        this.gsm.update();
    }

    private void draw() {
        this.gsm.draw(g);
    }

    private void drawToScreen() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(this.image, 0, 0,this.WIDTH * this.SCALE,this.HEIGHT * this.SCALE, null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {
    }

    public void keyPressed(KeyEvent key) {
        gsm.keyPressed(key.getKeyCode());
    }

    public void keyReleased(KeyEvent key) {
        gsm.keyReleased(key.getKeyCode());
    }

}
