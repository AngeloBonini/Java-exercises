import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Cursor extends JFrame  {

    Cursor() {
        super("getCursor Example");
        add(new Grafico());
        setSize(200, 200);
        setVisible(true);
    }

    static public void main(String[] args) {
        new Cursor();
    }
}

class Grafico extends Canvas implements Runnable {
    int curX, curY;
    
  Grafico() {
        (new Thread(this)).start();
        addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
            if (getCursor().getType() != java.awt.Cursor.WAIT_CURSOR) {
                curX = e.getX();
                curY = e.getY();
                repaint();
            }
      }
    });
  }
        
    public void paint(Graphics g) {
        System.out.println("y");
        g.fillOval(curX-5, curY-5, 10, 10);
    }

    public void run() {
        while (true) {
            try {
                if (getCursor().getType() == java.awt.Cursor.WAIT_CURSOR) {
                    setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.CROSSHAIR_CURSOR));
                } else {
                    setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
                }
                Thread.sleep(3000);
            } catch (InterruptedException e) {};
        }
    }
}