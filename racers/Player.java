import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Player {
    private Component parentComponent;

    private final int numberOfPlayer;

    private final short WIDTH = 200, HEIGHT = 150;
    private int posX, posY;
    private final int moveY = 10; 
    private Image loaded;
    private int state;
    private int verticalDirection;

    private final int LIMIT_Y_TOP = 210;
    private final int LIMIT_Y_BOTTOM = 415;

    Player(Component parentComponent, int numberOfPlayer, int initialPosX, int initialPosY) {
        this.parentComponent = parentComponent;
        this.numberOfPlayer = numberOfPlayer;


        posX = initialPosX;
        posY = initialPosY;
        state = 0;
        verticalDirection = 1;

    }
         // Pintar Estado (Movimento/Ataque)
    //      public void paintState(Graphics g) {
    //         // Parado
    //         if (state == 5) {
    //             this.posY-= 10;
    //             System.out.println(this.posY);
    //             // posY -= verticalDirection * moveY;
  
    //             // Colisão
    //             if (posY < LIMIT_Y_TOP) {
    //                  posY = LIMIT_Y_TOP;
    //             } else if (posY > LIMIT_Y_BOTTOM) {
    //                  posY = LIMIT_Y_BOTTOM;
    //             }
 
      
    //         }

  
    //    }

    public void paintShip( Graphics g){
        loaded = loadImage("pixil-frame-0.png");
    g.drawImage(loaded, getPosX() , getPosY() , parentComponent);
    }

    public KeyAdapter commands(){
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event){
                    // Movimento
                    // Cima
                    if (event.getKeyCode() == KeyEvent.VK_W) {
                        getPosY();
                        posY = posY - 10;
                        state = 5;
                        verticalDirection = 1;
                        // g.drawImage(loaded, getPosX() , getPosY() , parentComponent);
                        // Colisão
                        // if (posY > LIMIT_Y_TOP) {
                        //      state = 5;
                        //      verticalDirection = -1;
                        // }
                   }
                   // Baixo
                   if (event.getKeyCode() == KeyEvent.VK_S) {
                    posY = posY + 10;
                    state = 5;
                             verticalDirection = 1;
                            //  g.drawImage(loaded, getPosX() , getPosY() , parentComponent);
                        // Colisão
                        // if (posY < LIMIT_Y_BOTTOM) {
                        //      state = 5;
                        //      verticalDirection = 1;
                        // }
                   }
            }
        };
    }
    public int getPosX() {
        return this.posX;
   }

    public int getPosY() {
        System.out.println(this.posY);
        return this.posY;
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
