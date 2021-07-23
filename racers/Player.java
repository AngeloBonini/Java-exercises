import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Player {
     static final int PARADO = 0;
     static final int SOBE = 1;
     static final int DESCE = 2;

      int posX, posY;
      int estado = PARADO;
      Rectangle rectColisao;
      int pontos = 0;
    // private Component parentComponent;

    // private final int numberOfPlayer;

    private final short WIDTH = 200, HEIGHT = 150;
    private final int moveY = 10; 
    private Image loaded;


    Player() {
     inicia();
        // verticalDirection = 1;

    }

    Player(int x, int y){
        inicia();
        posicao(x, y);
    }

    void inicia(){

    }

    // public void paintShip( Graphics g){
    //     loaded = loadImage("pixil-frame-0.png");
    // g.drawImage(loaded, getPosX() , getPosY() , parentComponent);
    // }


    public int getPosX() {
        return this.posX;
   }

    public int getPosY() {
        // System.out.println(this.posY);
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
