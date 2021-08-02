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

    JButton soma = new JButton("Soma");
    JButton subtracao = new JButton("Subtrai");
    JButton multiplicacao = new JButton("Multiplica");
    JButton divisao = new JButton("Divide");

    public class Soma implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int primeiroInput = Integer.parseInt(textA1.getText());
            int segundoInput = Integer.parseInt(textA2.getText());
            result.setText(Integer.toString(primeiroInput + segundoInput));
        }
    }

    public class Subtrair implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int primeiroInput = Integer.parseInt(textA1.getText());
            int segundoInput = Integer.parseInt(textA2.getText());
            result.setText(Integer.toString(primeiroInput - segundoInput));
        }
    }

    public class Multiplica implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int primeiroInput = Integer.parseInt(textA1.getText());
            int segundoInput = Integer.parseInt(textA2.getText());
            result.setText(Integer.toString(primeiroInput * segundoInput));
        }
    }

    public class Divide implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int primeiroInput = Integer.parseInt(textA1.getText());
            int segundoInput = Integer.parseInt(textA2.getText());
            result.setText(Integer.toString(primeiroInput / segundoInput));
        }
    }

    exergui2() {
        super("Calculadora");
        setLayout(new FlowLayout());

        soma.addActionListener(new Soma());
        subtracao.addActionListener(new Subtrai());
        multiplicacao.addActionListener(new Multiplica());
        divisao.addActionListener(new Divide());
        Pbuttons.add(soma);
        Pbuttons.add(subtracao);
        Pbuttons.add(multiplicacao);
        Pbuttons.add(divisao);
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