import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Board extends JPanel implements Runnable {

   
  
    private LinkedList<Asteroid> asteroids;
    private LinkedList<Bullet> bullets;
    private Player localPlayer;
    private Player nonPlayable;
    private int score = 0;
   
    private static BufferedImage backgroundImage;
    private static BufferedImage redPlayerImage;
    private static BufferedImage bluePlayerImage;
    private static BufferedImage[] asteroidImage = new BufferedImage[3];
    private static BufferedImage bulletImage;
    private boolean isPaused = true;
    private int asteroidTimer = 0;
    private float FPS;
  
    private int xiAsteroid = 20;
  
    private int xAsteroid;

    private int yiAsteroid = 10;
   
    public Board() {
      
        asteroids = new LinkedList<>();
        bullets = new LinkedList<>();
        setFocusable(true);
        setVisible(true);
        setPreferredSize(new Dimension(800, 600));
        addKeyboardListener();
        loadResources();
        setUpPlayers();
        FPS = 60;
        
    }

    private void loadResources() {
        backgroundImage = loadImage("background.png");
        redPlayerImage = loadImage("spaceship_vermelho.png");
        bluePlayerImage = loadImage("spaceship_azul.png");
        asteroidImage[0] = loadImage("asteroid1.png");
        asteroidImage[1] = loadImage("asteroid2.png");
        asteroidImage[2] = loadImage("asteroid3.png");
        bulletImage = loadImage("projetil.png");
    }

    private void setUpPlayers() {
        localPlayer = new Player(new Point(), new Dimension(bluePlayerImage.getWidth(), bluePlayerImage.getHeight()));
        nonPlayable = new Player(new Point(), new Dimension(redPlayerImage.getWidth(), redPlayerImage.getHeight()));
    }

   
    private void addKeyboardListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F2) {
                    startGame();
                }

                if (!isPaused && localPlayer.isAlive()) {
                  
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        localPlayer.getPosition().x -= 10;

                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                        localPlayer.getPosition().x += 10;

                    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) { 

                        Point bulletPoint = new Point(localPlayer.getPosition().x + localPlayer.getSize().width / 2,
                                localPlayer.getPosition().y);
                        fire(bulletPoint);

                    }
                }
            }
        });
    }

    private void startGame() {
        localPlayer.setPosition(new Point((int) this.getPreferredSize().getWidth() - 100,
                    (int) this.getPreferredSize().getHeight() - localPlayer.getSize().height - 20));
        nonPlayable.setPosition(new Point((int) this.getPreferredSize().getWidth() - 100,
                (int) this.getPreferredSize().getHeight() - nonPlayable.getSize().height - 20));
        isPaused = false;

    }

    // private void createAsteroid(Point pos, Point speed, int radius) {
    //     Dimension size = new Dimension(asteroidImage[radius].getWidth(), asteroidImage[radius].getHeight());
    //     asteroids.addFirst(new Asteroid(pos, speed, size, radius));
    // }

    private void fire(Point pos) {
        bullets.add(new Bullet(pos, new Dimension(bulletImage.getWidth(), bulletImage.getHeight())));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.drawImage(backgroundImage, null, 0, 0);

        for (Asteroid a : asteroids) {
            g2d.drawImage(asteroidImage[a.getRadius()], a.getPosition().x, a.getPosition().y, null);
        }

        for (Bullet b : bullets) {
            g2d.drawImage(bulletImage, b.getPosition().x, b.getPosition().y, null);
        }

        if (localPlayer.isAlive()) {
            g2d.drawImage(bluePlayerImage, localPlayer.getPosition().x, localPlayer.getPosition().y, null);
        }

        if (nonPlayable.isAlive()) {
            g2d.drawImage(redPlayerImage, nonPlayable.getPosition().x, nonPlayable.getPosition().y, null);
        }

        g2d.setColor(new Color(128, 0, 255));
        g2d.setFont(new Font("Segoe UI Light", Font.BOLD, 36));
        g2d.drawString("Score:" + score, this.getWidth() - 200, 50);
    }

    @Override
    public void run() {
        while (!isPaused) {
            try {
            //     createAsteroid(
            //         new Point(Integer.parseInt(args[0]), Integer.parseInt(args[1])),
            //         new Point(Integer.parseInt(args[2]), Integer.parseInt(args[3])),
            //         Integer.parseInt(args[4])
            // );
                asteroidTimer += FPS;

                LinkedList<Asteroid> auxAsteroid = (LinkedList<Asteroid>) asteroids.clone();
                LinkedList<Bullet> auxBullet = (LinkedList<Bullet>) bullets.clone();

                for (Asteroid a : asteroids) {
                    a.getPosition().y += a.getSpeed().y;

                    if (localPlayer.isAlive()) {
                        if (collisionDetection(a, localPlayer)) {
                            localPlayer.setAlive(false);
                        }
                    }

                    if (nonPlayable.isAlive()) {
                        if (collisionDetection(a, nonPlayable)) {
                            nonPlayable.setAlive(false);
                        }
                    }

                    for (Bullet b : bullets) {
                        if (collisionDetection(a, b)) {
                            auxAsteroid.remove(a);
                            auxBullet.remove(b);
                            score += 10;
                        }
                    }

                    if (a.getPosition().y > this.getHeight()) {
                        auxAsteroid.remove(a);
                    }
                }
                asteroids = auxAsteroid;

                for (Bullet b : bullets) {
                    b.getPosition().y -= b.getSpeed().y;
                    if (b.getPosition().y < 0) {
                        auxBullet.remove(b);
                    }
                }
                bullets = auxBullet;

                repaint();
            } catch (Exception e) {
            }
        }
    }

    public BufferedImage loadImage(String fileName) {
        try {
            System.out.println(getClass().getResource("/img/" + fileName));
            return ImageIO.read(getClass().getResource("/img/" + fileName));
        } catch (IOException e) {
            System.out.println("Content could not be read");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Graphics g) {
        super.paint(g);
        Image offScreen = null;
        Graphics offGraphics = null;
        Graphics2D g2d = (Graphics2D) g.create();
        Dimension dimension = getSize();
        offScreen = createImage(dimension.width, dimension.height);
        offGraphics = offScreen.getGraphics();
        offGraphics.setColor(getBackground());
        offGraphics.fillRect(0, 0, dimension.width, dimension.height);
        offGraphics.setColor(Color.black);
        paint(offGraphics);
        g2d.drawImage(offScreen, 0, 0, null);
        g2d.dispose();
    }


    private boolean collisionDetection(Sprite sprite1, Sprite sprite2) {
        Point pos1 = sprite1.getPosition();
        Point pos2 = sprite2.getPosition();
        int w1 = sprite1.getSize().width;
        int h1 = sprite1.getSize().height;
        int w2 = sprite2.getSize().width;
        int h2 = sprite2.getSize().height;
        return (((pos1.x > pos2.x && pos1.x < pos2.x + w2) && (pos1.y > pos2.y && pos1.y < pos2.y + h2))
                || ((pos2.x > pos1.x && pos2.x < pos1.x + w1) && (pos2.y > pos1.y && pos2.y < pos1.y + h1)));
    }

    private class Asteroid extends Sprite {

        private int radius = 0;

        public Asteroid(Point position, Dimension size) {
            this.setPosition(position);
            this.setSize(size);
            this.setSpeed(new Point(0, 5));
        }

        public Asteroid(Point position, Point speed, Dimension size, int radius) {
            this(position, size);
            this.setSpeed(speed);
            this.setRadius(radius);
        }

        public int getRadius() {
            return this.radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }
    }

    private class Bullet extends Sprite {

        public Bullet(Point position, Dimension size) {
            this.setPosition(position);
            this.setSize(size);
            this.setSpeed(new Point(0, 10));
        }
    }

    private class Player extends Sprite {


        private boolean isAlive;

        public Player(Point position, Dimension size) {
            this.setPosition(position);
            this.setSize(size);
            this.isAlive = true;
        }

        public boolean isAlive() {
            return this.isAlive;
        }

        public void setAlive(boolean isAlive) {
            this.isAlive = isAlive;
        }
    }
}