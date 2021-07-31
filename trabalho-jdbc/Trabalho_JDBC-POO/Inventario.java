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

public class Inventario extends Janela {
    JPanel Campos[] = new JPanel[4];
    JTable Tabela;
    JTextArea InsNome, InsPreco, InsQuantidade, InsTipo, InsDescricao, AltID, AltNome, AltPreco, AltQuantidade, AltTipo, AltDescricao, ExcID;
    JButton BInserir, BAlterar, BExcluir;

    Inventario() {
        super("Inventario");
        CriaBancoDados("BD-Inve");
        try {
            Afirmacao.executeUpdate("CREATE TABLE INVENTARIO (NOME VARCHAR(50), PRECO FLOAT, QUANTIDADE INT, TIPO VARCHAR(25), DESCRICAO VARCHAR(100))");
        } catch (SQLException excecao) {}
        
        Border borda = BorderFactory.createLineBorder(Color.black);

        for (int i = 0; i < 4; i++) Campos[i] = new JPanel();

        Campos[0].setLayout(new GridLayout(6, 2));
        Campos[0].add(new JLabel("Registrar produto"));
        BInserir = new JButton("Registrar");
        BInserir.addActionListener(new Insere());
        Campos[0].add(BInserir);
        Campos[0].add(new JLabel("Nome"));
        InsNome = new JTextArea();
        InsNome.setBorder(borda);
        Campos[0].add(InsNome);
        Campos[0].add(new JLabel("Preco unitario"));
        InsPreco = new JTextArea();
        InsPreco.setBorder(borda);
        Campos[0].add(InsPreco);
        Campos[0].add(new JLabel("Quantidade"));
        InsQuantidade = new JTextArea();
        InsQuantidade.setBorder(borda);
        Campos[0].add(InsQuantidade);
        Campos[0].add(new JLabel("Tipo"));
        InsTipo = new JTextArea();
        InsTipo.setBorder(borda);
        Campos[0].add(InsTipo);
        Campos[0].add(new JLabel("Descricao"));
        InsDescricao = new JTextArea();
        InsDescricao.setBorder(borda);
        Campos[0].add(InsDescricao);

        Campos[1].setLayout(new GridLayout(7, 2));
        Campos[1].add(new JLabel("Alterar registro"));
        BAlterar = new JButton("Alterar");
        BAlterar.addActionListener(new Altera());
        Campos[1].add(BAlterar);
        Campos[1].add(new JLabel("Nome do produto cujo registro sera alterado"));
        AltID = new JTextArea();
        AltID.setBorder(borda);
        Campos[1].add(AltID);
        Campos[1].add(new JLabel("Nome"));
        AltNome = new JTextArea();
        AltNome.setBorder(borda);
        Campos[1].add(AltNome);
        Campos[1].add(new JLabel("Preco unitario"));
        AltPreco = new JTextArea();
        AltPreco.setBorder(borda);
        Campos[1].add(AltPreco);
        Campos[1].add(new JLabel("Quantidade"));
        AltQuantidade = new JTextArea();
        AltQuantidade.setBorder(borda);
        Campos[1].add(AltQuantidade);
        Campos[1].add(new JLabel("Tipo"));
        AltTipo = new JTextArea();
        AltTipo.setBorder(borda);
        Campos[1].add(AltTipo);
        Campos[1].add(new JLabel("Descricao"));
        AltDescricao = new JTextArea();
        AltDescricao.setBorder(borda);
        Campos[1].add(AltDescricao);

        Campos[2].setLayout(new GridLayout(2, 2));
        Campos[2].add(new JLabel("Excluir registro"));
        BExcluir = new JButton("Excluir");
        BExcluir.addActionListener(new Exclui());
        Campos[2].add(BExcluir);
        Campos[2].add(new JLabel("Nome do produto cujo registro sera apagado"));
        ExcID = new JTextArea();
        ExcID.setBorder(borda);
        Campos[2].add(ExcID);

        Tabela = new JTable(new Object[1][6], new String[] {"Nome", "Tipo", "Preco", "Quantidade", "Descricao"});
        Campos[3].setLayout(new GridLayout(2, 1));
        Campos[3].add(Tabela.getTableHeader());
        Campos[3].add(Tabela);

        for (int i = 0; i < 4; i++) Campos[i].setBorder(borda);
        AtualizaJanela();
        CriaJanela(Campos);
    }
    public void AtualizaJanela() {
        DefaultTableModel Modelo = new DefaultTableModel(new Object[] {"Nome", "Preco unitario", "Quantidade", "Tipo", "Descricao"}, 0);
        try {
            ResultSet Dados = Afirmacao.executeQuery("SELECT * FROM INVENTARIO");
            while (Dados.next()) {
                String Nome = Dados.getString("NOME");
                float Preco = Dados.getFloat("PRECO");
                int Quantidade = Dados.getInt("Quantidade");
                String Tipo = Dados.getString("TIPO");
                String Descricao = Dados.getString("DESCRICAO");
                Modelo.addRow(new Object[] {Nome, Preco, Quantidade, Tipo, Descricao});
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
            String Nome = InsNome.getText();
            String preco = InsPreco.getText();
            String quantidade = InsQuantidade.getText();
            String Tipo = InsTipo.getText();
            String Descricao = InsDescricao.getText();
            if (Nome.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insira um nome.");
                return;
            }
            if (Nome.length() > 50) {
                JOptionPane.showMessageDialog(null, "Insira um nome com 50 caracteres ou menos.");
                return;
            }
            if (preco.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um preco.");
                return;
            }
            float Preco;
            try {
                Preco = Float.parseFloat(preco);
            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(null,"Insira um numero real como preco.");
                return;
            }
            int Quantidade;
            try {
                Quantidade = Integer.parseInt(quantidade);
            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(null,"Insira um numero inteiro como quantidade.");
                return;
            }
            if (Tipo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insira um tipo.");
                return;
            }
            if (Tipo.length() > 50) {
                JOptionPane.showMessageDialog(null, "Insira um tipo com 25 caracteres ou menos.");
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
                Afirmacao.executeUpdate("INSERT INTO INVENTARIO VALUES('"+ Nome +"', "+ Preco +", "+ Quantidade +", '"+ Tipo +"', '"+ Descricao +"')");
            } catch(SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            AtualizaJanela();
        }
    }
    class Altera implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String NomeEspecificado = AltID.getText();
            String Nome = AltNome.getText();
            String preco = AltPreco.getText();
            String quantidade = AltQuantidade.getText();
            String Tipo = AltTipo.getText();
            String Descricao = AltDescricao.getText();
            if (NomeEspecificado.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um nome como especificacao.");
                return;
            }
            if (NomeEspecificado.length() > 50) {
                JOptionPane.showMessageDialog(null,"Insira um nome com 50 caracteres ou menos como especificacao.");
                return;
            }
            if (Nome.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um nome.");
                return;
            }
            if (Nome.length() > 50) {
                JOptionPane.showMessageDialog(null,"Insira um nome com menos de 50 caracteres.");
                return;
            }
            float Preco;
            try {
                Preco = Float.parseFloat(preco);
            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(null,"Insira um numero real como preco.");
                return;
            }
            int Quantidade;
            try {
                Quantidade = Integer.parseInt(quantidade);
            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(null,"Insira um numero inteiro como quantidade.");
                return;
            }
            if (Tipo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Insira um tipo.");
                return;
            }
            if (Tipo.length() > 50) {
                JOptionPane.showMessageDialog(null, "Insira um tipo com 25 caracteres ou menos.");
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
                Afirmacao.executeUpdate("UPDATE INVENTARIO SET NOME='"+ Nome +"', PRECO="+ Preco +", QUANTIDADE="+ Quantidade +", TIPO='"+ Tipo +"', DESCRICAO='"+ Descricao +"' WHERE NOME='"+ NomeEspecificado +"'");
            } catch(SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            AtualizaJanela();
        }
    }
    class Exclui implements ActionListener {
        public void actionPerformed(ActionEvent evento) {
            String Nome = ExcID.getText();
            if (Nome.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um nome para especificar o cadastro que deseja excluir.");
                return;
            }
            if (Nome.length() > 50) {
                JOptionPane.showMessageDialog(null,"Insira um nome com 50 ou menos caracteres para especificar o cadastro que deseja excluir.");
                return;
            }
            try {
                Afirmacao.executeUpdate("DELETE FROM INVENTARIO WHERE NOME='"+ Nome +"'");
            } catch(SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            AtualizaJanela();
        }
    }
}