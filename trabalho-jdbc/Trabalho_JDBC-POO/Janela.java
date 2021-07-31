import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Janela extends JFrame {
    Connection Conexao;
    Statement Afirmacao;

    Janela(String Titulo) {
        super(Titulo);
    }
    void CriaJanela(JPanel Campos[]) {
        setLayout(new GridLayout(Campos.length, 1));
        for (int i = 0; i < Campos.length; i++) add(Campos[i]);

        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void CriaBancoDados(String BD) {
        try {
            Class.forName("org.hsql.jdbcDriver");
            Conexao = DriverManager.getConnection("jdbc:HypersonicSQL:" + BD, "sa", "");
            Afirmacao = Conexao.createStatement();
        } catch (ClassNotFoundException excecao) {
            excecao.printStackTrace();
            System.exit(1);
        } catch (SQLException excecao) {
            excecao.printStackTrace();
            System.exit(1);
        }
    }
}