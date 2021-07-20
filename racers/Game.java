import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Game extends JFrame {

    private RunGame panel;

    final static short FRAME_WIDTH = 600;
    final static short FRAME_HEIGHT = 1000;

    private Timer t;

    Game() {
        super("Space Racers - 1973");

        panel = new RunGame();

        add(panel);
        pack();

        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);


        addKeyListener(panel.player1.commands());
    }


     class RunGame extends JPanel {
        private BufferedImage background;

        private Player player1;

        private Player player2;

        RunGame() {
             setPreferredSize(new Dimension(Game.FRAME_WIDTH, Game.FRAME_HEIGHT));

             background = loadImage("background.jpeg");

             player1 = new Player(this, 1, 200, 850);

             player2 = new Player(this, 2, 350, 850);
        }

        @Override
        protected void paintComponent(Graphics g) {
             super.paintComponent(g);
             Graphics2D g2d = (Graphics2D) g.create();
     
            g2d.drawImage(background, null, 0, 0);
       
             player1.paintShip(g);
         
             player2.paintShip(g);
       
             Toolkit.getDefaultToolkit().sync();

        }


   }
   public static void main(String[] args) {
    new Game();
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
}
