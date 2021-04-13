package basic;
import java.awt.*;
import javax.swing.*;

public class Standalone extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public void paint(Graphics g ){
        g.drawString("Hello World! ", 40, 100);
    }      
    public static void main(String args[]){
        Standalone hello= new Standalone();
        hello.setSize(155, 150);
        hello.setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE);
        hello.setVisible(true);
    }
}
