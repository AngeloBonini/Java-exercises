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

public class Assinantes extends Janela {
    JPanel Campos[] = new JPanel[4];
    JTable Tabela;
    JTextArea InsNome, InsCartao, InsEndereco, InsPlano, AltID, AltCartao, AltNome, AlteraEndereco, AltPlano, ExcID;
    JButton BInserir, BAlterar, BExcluir;
    
    Assinantes() {
        super("Assinantes");
        CriaBancoDados("BD-Assinantes");
        try {
            Afirmacao.executeUpdate("CREATE TABLE ASSINANTES ( NOME VARCHAR(50), ENDERECO VARCHAR(25),  CARTAO VARCHAR(25), PLANO VARCHAR(50))");
        } catch (SQLException excecao) {}
        Border borda = BorderFactory.createLineBorder(Color.black);

        for (int i = 0; i < 4; i++) Campos[i] = new JPanel();

        Campos[0].setLayout(new GridLayout(5, 2));
        Campos[0].add(new JLabel("Cadastrar funcionario"));
        BInserir = new JButton("Cadastrar");
        BInserir.addActionListener(new Insere());
        Campos[0].add(BInserir);
        Campos[0].add(new JLabel("Cartao"));
        InsCartao = new JTextArea();
        InsCartao.setBorder(borda);
        Campos[0].add(InsCartao);
        Campos[0].add(new JLabel("Nome"));
        InsNome = new JTextArea();
        InsNome.setBorder(borda);
        Campos[0].add(InsNome);
        Campos[0].add(new JLabel("Endereco"));
        InsEndereco = new JTextArea();
        InsEndereco.setBorder(borda);
        Campos[0].add(InsEndereco);
        Campos[0].add(new JLabel("Plano de asssinatura"));
        InsPlano = new JTextArea();
        InsPlano.setBorder(borda);
        Campos[0].add(InsPlano);

        Campos[1].setLayout(new GridLayout(6, 2));
        Campos[1].add(new JLabel("Alterar cadastro"));
        BAlterar = new JButton("Alterar");
        BAlterar.addActionListener(new Altera());
        Campos[1].add(BAlterar);
        Campos[1].add(new JLabel("Nome do assinante para alteracao do cadastro "));
        AltID = new JTextArea();
        AltID.setBorder(borda);
        Campos[1].add(AltID);
        Campos[1].add(new JLabel("Novo Cartao"));
        AltCartao = new JTextArea();
        AltCartao.setBorder(borda);
        Campos[1].add(AltCartao);
        Campos[1].add(new JLabel("Novo nome"));
        AltNome = new JTextArea();
        AltNome.setBorder(borda);
        Campos[1].add(AltNome);
        Campos[1].add(new JLabel("Novo endereco"));
        AlteraEndereco = new JTextArea();
        AlteraEndereco.setBorder(borda);
        Campos[1].add(AlteraEndereco);
        Campos[1].add(new JLabel("Novo Plano de assinatura"));
        AltPlano = new JTextArea();
        AltPlano.setBorder(borda);
        Campos[1].add(AltPlano);

        Campos[2].setLayout(new GridLayout(2, 2));
        Campos[2].add(new JLabel("Excluir cadastro"));
        BExcluir = new JButton("Excluir");
        BExcluir.addActionListener(new Exclui());
        Campos[2].add(BExcluir);
        Campos[2].add(new JLabel("Nome do assinante para exclusao"));
        ExcID = new JTextArea();
        ExcID.setBorder(borda);
        Campos[2].add(ExcID);

        Tabela = new JTable(new Object[1][5], new String[] {"ID", "cartao", "Nome", "Endereco", "Plano de assinatura"});
        Campos[3].setLayout(new GridLayout(2, 1));
        Campos[3].add(Tabela.getTableHeader());
        Campos[3].add(Tabela);

        for (int i = 0; i < 4; i++) Campos[i].setBorder(borda);
        AtualizaJanela();
        CriaJanela(Campos);
    }

    public void AtualizaJanela() {
        DefaultTableModel Modelo = new DefaultTableModel(new Object[] {"Cartao", "Nome", "Endereco", "Plano de assinatura"}, 0);
        try {
            ResultSet Dados = Afirmacao.executeQuery("SELECT * FROM ASSINANTES");
            while (Dados.next()) {
                String Cartao = Dados.getString("CARTAO");
                String Nome = Dados.getString("NOME");
                float Endereco = Dados.getFloat("ENDERECO");
                String Plano = Dados.getString("PLANO");
                Modelo.addRow(new Object[] {Cartao, Nome, Endereco, Plano});
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
            String Cartao = InsCartao.getText();
            String Endereco = InsEndereco.getText();
            String Plano = InsPlano.getText();
            if (Cartao.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira o numero do cartao.");
                return;
            }
            if (Cartao.length() > 25) {
                JOptionPane.showMessageDialog(null,"Insira um numero com menos de 25 caracteres.");
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
            if (Endereco.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um Endereco.");
                return;
            }
     
            if (Plano.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira uma descricao.");
                return;
            }
            if (Plano.length() > 100) {
                JOptionPane.showMessageDialog(null,"Insira uma descricao com menos de 100 caracteres.");
                return;
            }
            try {
                Afirmacao.executeUpdate("INSERT INTO FUNCIONARIOS VALUES('"+ Cartao +"', '"+ Nome +"', "+ Endereco +", '"+ Plano +"')");
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
            String Cartao = AltCartao.getText();
            String Nome = AltNome.getText();
            String Endereco = AlteraEndereco.getText();
            String Plano = AltPlano.getText();
            if (NomeEspecificado.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um nome como especificacao.");
                return;
            }
            if (NomeEspecificado.length() > 50) {
                JOptionPane.showMessageDialog(null,"Insira um nome com 50 caracteres ou menos como especificacao.");
                return;
            }
            if (Cartao.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um cargo.");
                return;
            }
            if (Cartao.length() > 25) {
                JOptionPane.showMessageDialog(null,"Insira um cargo com menos de 25 caracteres.");
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
            if (Endereco.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um Endereco.");
                return;
            }
      
            if (Plano.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um plano de assinatura.");
                return;
            }
            if (Plano.length() > 100) {
                JOptionPane.showMessageDialog(null,"Insira uma descricao com menos de 100 caracteres.");
                return;
            }
            try {
                Afirmacao.executeUpdate("UPDATE ASSINANTES SET CARTAO='"+ Cartao +"', NOME='"+ Nome +"', ENDERCO="+ Endereco +", PLANO='"+ Plano +"' WHERE NOME='"+ NomeEspecificado +"'");
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
                Afirmacao.executeUpdate("DELETE FROM ASSINANTES WHERE NOME='"+ Nome +"'");
            } catch(SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            AtualizaJanela();
        }
    }
}