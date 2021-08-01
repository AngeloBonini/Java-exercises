import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.SQLException;
import java.sql.ResultSet;

public class Musicas extends Janela {
    JPanel Campos[] = new JPanel[4];
    JTable Tabela;
    JTextArea InsCliente, InsValor, InsDescricao, AltID, AltCliente, AltValor, AltDescricao, ExcID;
    JButton BInserir, BAlterar, BExcluir;

    Musicas() {
        super("Musicas");
        CriaBancoDados("BD-Musicas");
        try {
            Afirmacao.executeUpdate("CREATE TABLE MUSICAS (TITULO VARCHAR(50), DURACAO FLOAT, LETRA VARCHAR(100))");
        } catch (SQLException excecao) {}

        Border borda = BorderFactory.createLineBorder(Color.black);

        for (int i = 0; i < 4; i++) Campos[i] = new JPanel();

        Campos[0].setLayout(new GridLayout(4, 2));
        Campos[0].add(new JLabel("Cadastrar pedido"));
        BInserir = new JButton("Cadastrar");
        BInserir.addActionListener(new Insere());
        Campos[0].add(BInserir);
        Campos[0].add(new JLabel("Cliente"));
        InsCliente = new JTextArea();
        InsCliente.setBorder(borda);
        Campos[0].add(InsCliente);
        Campos[0].add(new JLabel("Valor"));
        InsValor = new JTextArea();
        InsValor.setBorder(borda);
        Campos[0].add(InsValor);
        Campos[0].add(new JLabel("Descricao"));
        InsDescricao = new JTextArea();
        InsDescricao.setBorder(borda);
        Campos[0].add(InsDescricao);

        Campos[1].setLayout(new GridLayout(5, 2));
        Campos[1].add(new JLabel("Alterar pedido"));
        BAlterar = new JButton("Alterar");
        BAlterar.addActionListener(new Altera());
        Campos[1].add(BAlterar);
        Campos[1].add(new JLabel("Nome do cliente cujo pedido sera alterado"));
        AltID = new JTextArea();
        AltID.setBorder(borda);
        Campos[1].add(AltID);
        Campos[1].add(new JLabel("Cliente"));
        AltCliente = new JTextArea();
        AltCliente.setBorder(borda);
        Campos[1].add(AltCliente);
        Campos[1].add(new JLabel("Valor"));
        AltValor = new JTextArea();
        AltValor.setBorder(borda);
        Campos[1].add(AltValor);
        Campos[1].add(new JLabel("Descricao"));
        AltDescricao = new JTextArea();
        AltDescricao.setBorder(borda);
        Campos[1].add(AltDescricao);

        Campos[2].setLayout(new GridLayout(2, 2));
        Campos[2].add(new JLabel("Excluir cadastro"));
        BExcluir = new JButton("Excluir");
        BExcluir.addActionListener(new Exclui());
        Campos[2].add(BExcluir);
        Campos[2].add(new JLabel("Nome do cliente cujo pedido sera excluido"));
        ExcID = new JTextArea();
        ExcID.setBorder(borda);
        Campos[2].add(ExcID);

        Tabela = new JTable(new Object[1][4], new String[] {"Cliente", "Valor", "Descricao"});
        Campos[3].setLayout(new GridLayout(2, 1));
        Campos[3].add(Tabela.getTableHeader());
        Campos[3].add(Tabela);

        for (int i = 0; i < 4; i++) Campos[i].setBorder(borda);
        AtualizaJanela();
        CriaJanela(Campos);
    }
    public void AtualizaJanela() {
        DefaultTableModel Modelo = new DefaultTableModel(new Object[] {"Cliente", "Valor", "Descricao"}, 0);
        try {
            ResultSet Dados = Afirmacao.executeQuery("SELECT * FROM MUSICAS");
            while (Dados.next()) {
                String Cliente = Dados.getString("TITULO");
                float Valor = Dados.getFloat("DURACAO");
                String Descricao = Dados.getString("LETRA");
                Modelo.addRow(new Object[] {Cliente, Valor, Descricao});
                Dados.next();
            }
        } catch (SQLException erro) {
            erro.printStackTrace();
            System.exit(1);
        }
        Tabela.setModel(Modelo);
    }
    class Insere implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String Cliente = InsCliente.getText();
            String valor = InsValor.getText();
            String Descricao = InsDescricao.getText();
            if (Cliente.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insira um cliente.");
                return;
            }
            if (Cliente.length() > 50) {
                JOptionPane.showMessageDialog(null, "Insira um nome com 50 caracteres ou menos.");
                return;
            }
            if (valor.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um valor.");
                return;
            }
            float Valor;
            try {
                Valor = Float.parseFloat(valor);
            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(null,"Insira um numero real como valor.");
                return;
            }
            if (Descricao.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insira uma descricao.");
                return;
            }
            if (Descricao.length() > 100) {
                JOptionPane.showMessageDialog(null, "Insira uma descricao com 100 caracteres ou menos.");
                return;
            }
            try {
                Afirmacao.executeUpdate("INSERT INTO MUSICAS VALUES('"+ Cliente +"', "+ Valor +", '"+ Descricao +"')");
            } catch(SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            AtualizaJanela();
        }
    }
    class Altera implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String ClienteEspecificado = AltID.getText();
            String Cliente = AltCliente.getText();
            String valor = AltValor.getText();
            String Descricao = AltDescricao.getText();
            if (ClienteEspecificado.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insira um nome como especificacao.");
                return;
            }
            if (ClienteEspecificado.length() > 50) {
                JOptionPane.showMessageDialog(null, "Insira um nome com 50 caracteres ou menos como especificacao.");
                return;
            }
            if (Cliente.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insira um cliente.");
                return;
            }
            if (Cliente.length() > 50) {
                JOptionPane.showMessageDialog(null, "Insira um nome com 50 caracteres ou menos.");
                return;
            }
            if (valor.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um valor.");
                return;
            }
            float Valor;
            try {
                Valor = Float.parseFloat(valor);
            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(null,"Insira um numero real como valor.");
                return;
            }
            if (Descricao.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insira uma descricao.");
                return;
            }
            if (Descricao.length() > 100) {
                JOptionPane.showMessageDialog(null, "Insira uma descricao com 100 caracteres ou menos.");
                return;
            }
            try {
                Afirmacao.executeUpdate("UPDATE MUSICAS SET TITULO='"+ Cliente +"', DURACAO='"+ Valor +"', LETRA='"+ Descricao +"' WHERE TITULO='"+ ClienteEspecificado +"'");
            } catch(SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            AtualizaJanela();
        }
    }
    class Exclui implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String Cliente = ExcID.getText();
            if (Cliente.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um nome como especificacao.");
                return;
            }
            if (Cliente.length() > 50) {
                JOptionPane.showMessageDialog(null,"Insira um nome com 50 ou menos caracteres como especificacao.");
                return;
            }
            try {
                Afirmacao.executeUpdate("DELETE FROM MUSICAS WHERE TITULO='"+ Cliente +"'");
            } catch(SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            AtualizaJanela();
        }
    }
}