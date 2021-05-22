import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.*;

public class calculator extends JFrame {
    
    
            JPanel editorPanel = new JPanel(new GridLayout(0, 1));
            JPanel left = new JPanel(new FlowLayout());
            JPanel right = new JPanel(new FlowLayout());
            JPanel center = new JPanel(new BorderLayout());
    calculator() {
        super("calculator");
        setLayout(new FlowLayout());
        editorPanel.add(new JButton("Soma"));
        editorPanel.add(new JButton("Subtrai"));
        editorPanel.add(new JButton("Multiplica"));
        editorPanel.add(new JButton("Divide"));
        center.add(editorPanel, BorderLayout.NORTH);
        // add(new JTextArea(10, 40), BorderLayout.EAST);
        left.add(new JTextField(10));
        right.add(new JTextField(10));
        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);
        add(new JLabel("= 0"));
        pack();
        setVisible(true);
    }

    public static void main(String[] args){
        new calculator();
    }
}
