import java.awt.*;
import javax.swing.*;

class BotaoDoidao extends JButton implements Runnable {
  int x, y;

  BotaoDoidao(String s) {
    super(s);
    new Thread(this).start();
  }

  public void run() {
    Point p;

    while (!isShowing())   //Espera que o botao esteja visivel
      try {
        Thread.sleep(10);
      } catch (Exception e) {}

    p=getLocation();
    x=p.x;
    y=p.y;
    while (true) {
      setBackground(new Color((float)Math.random(),
                    (float)Math.random(), (float)Math.random()));
      setLocation((int)(x+Math.random()*5-2), (int)(y+Math.random()*5-2));
      try {
        Thread.sleep((int)(Math.random()*500));
      } catch (Exception e) {}
    }
  }
}

class Psico1 extends JFrame {
  BotaoDoidao bd1=new BotaoDoidao("Botao 1");
  BotaoDoidao bd2=new BotaoDoidao("Botao 2");
  BotaoDoidao bd3=new BotaoDoidao("Botao 3");

  Psico1() {
    super("Psicodelico");
    setLayout(new FlowLayout());
    add(bd1);
    add(bd2);
    add(bd3);
    pack();
    setVisible(true);
  }

  public static void main(String[] args) {
    new Psico1();
  }
}