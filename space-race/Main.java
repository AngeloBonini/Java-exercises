import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Main {
    public static void main(String args[]){
        JFrame window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Asteroides - O Jogo");
        window.setLayout(new BorderLayout());
        window.add(new Board(), BorderLayout.CENTER);
        window.pack();
        window.setVisible(true);
    }
}
