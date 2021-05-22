import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.*;

public class calculator extends JFrame {
    
    String[]botoes = {"Soma", "Subtrai", "Multiplica", "Divide" };
            JPanel layout = new JPanel(new FlowLayout());
    calculator() {
        super("calculator");
        setLayout(new BorderLayout());

        layout.add(new JTextField(10));
        layout.add(new JComboBox<String>(botoes));
        layout.add(new JTextField(10));
        layout.add(new JLabel("= 0"));

        add(layout, BorderLayout.NORTH);
        add(new Button("Calcular"), BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    public static void main(String[] args){
        new calculator();
    }
}
