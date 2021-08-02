import java.awt.*;
import javax.swing.*;

class BotaoDoidao extends JButton implements Runnable {

  BotaoDoidao(String s) {
    super(s);
    new Thread(this).start();
  }

  public void run() {
    while (true) {
      setBackground(new Color((float)Math.random(),
                    (float)Math.random(), (float)Math.random()));
      try {
        Thread.sleep((int)(Math.random()*500));
      } catch (Exception e) {}
    }
  }
}

class Psico extends JFrame {
  BotaoDoidao bd1=new BotaoDoidao("Botao 1");
  BotaoDoidao bd2=new BotaoDoidao("Botao 2");
  BotaoDoidao bd3=new BotaoDoidao("Botao 3");

  Psico() {
    super("Psicodelico");
    setLayout(new FlowLayout());
    add(bd1);
    add(bd2);
    add(bd3);
    pack();
    setVisible(true);
  }

  public static void main(String[] args) {
    new Psico();
  }
}