import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.*;

public class exergui2 extends JFrame {
    JLabel result = new JLabel("0");
    JPanel Pbuttons = new JPanel(new GridLayout(0, 1));
    JPanel Pleft = new JPanel(new FlowLayout());
    JPanel Pcenter = new JPanel(new BorderLayout());
    JPanel Pright = new JPanel(new FlowLayout());

    exergui2() {
        super("Calculadora");
        setLayout(new FlowLayout());
        Pbuttons.add(new JButton("Soma"));
        Pbuttons.add(new JButton("Subtrai"));
        Pbuttons.add(new JButton("Multiplica"));
        Pbuttons.add(new JButton("Divide"));
        Pcenter.add(Pbuttons, BorderLayout.NORTH);
        Pleft.add(new JTextField(10));
        Pright.add(new JTextField(10));
        add(Pleft, BorderLayout.WEST);
        add(Pcenter, BorderLayout.CENTER);
        add(Pright, BorderLayout.EAST);
        add(new JLabel("="));
        add(result);
        setVisible(true);
        pack();
        
    }

    public static void main(String[] args) {
        new exergui2();
    }
}