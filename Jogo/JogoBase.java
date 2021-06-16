import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

class JogoBase extends JFrame {
  Image img[] = new Image[8] ;
  Desenho des = new Desenho();

  class Desenho extends JPanel {

    Desenho() {
      try {
        setPreferredSize(new Dimension(500, 3000));
        img[0] = ImageIO.read(new File("fundo.jpeg"));
        img[1] = ImageIO.read(new File("parado.gif"));
        img[2] = ImageIO.read(new File("anda0.gif"));
        img[3] = ImageIO.read(new File("anda1.gif"));
        img[4] = ImageIO.read(new File("soco0.gif"));
        img[5] = ImageIO.read(new File("soco1.gif"));
        img[6] = ImageIO.read(new File("soco2.gif"));
        img[7] = ImageIO.read(new File("arbusto.png"));
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "A imagem não pode ser carregada!\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(img[0], 0, 0, getSize().width, getSize().height, this);
      Toolkit.getDefaultToolkit().sync();
    }
  }
  class Naves {

  }

  JogoBase() {
    super("Trabalho");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    add(des);
    pack();
    setVisible(true);
  }

  static public void main(String[] args) {
    JogoBase f = new JogoBase();
  }
}
