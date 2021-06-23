import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

class MainGame extends JFrame {
  Image img[] = new Image[8] ;
  Desenho des = new Desenho();
  Ship horse = new Ship();

  class Desenho extends JPanel {

    Desenho() {
      try {
        setPreferredSize(new Dimension(600, 1000));
        img[0] = ImageIO.read(new File("background.jpeg"));
        img[1] = ImageIO.read(new File("pixil-frame-0.png"));
  
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "A imagem não pode ser carregada!\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(img[0], 0, 0, getSize().width, getSize().height, this);
      // g.drawImage(img[1], 100, 900, 30, 60, this);
      // g.drawImage(img[1], 400, 900, 30, 60, this);
      Toolkit.getDefaultToolkit().sync();
    }
  }

  MainGame() {
    super("Space Race");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    add(des);
    pack();
    setVisible(true);
  }

  static public void main(String[] args) {
    MainGame f = new MainGame();
  }
}
