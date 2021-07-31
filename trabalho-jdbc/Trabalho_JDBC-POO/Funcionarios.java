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

public class Funcionarios extends Janela {
    JPanel Campos[] = new JPanel[4];
    JTable Tabela;
    JTextArea InsNome, InsCargo, InsSalario, InsDescricao, AltID, AltCargo, AltNome, AltSalario, AltDescricao, ExcID;
    JButton BInserir, BAlterar, BExcluir;
    
    Funcionarios() {
        super("Funcionarios");
        CriaBancoDados("BD-Func");
        try {
            Afirmacao.executeUpdate("CREATE TABLE FUNCIONARIOS (CARGO VARCHAR(25), NOME VARCHAR(50), SALARIO FLOAT, DESCRICAO VARCHAR(100))");
        } catch (SQLException excecao) {}
        Border borda = BorderFactory.createLineBorder(Color.black);

        for (int i = 0; i < 4; i++) Campos[i] = new JPanel();

        Campos[0].setLayout(new GridLayout(5, 2));
        Campos[0].add(new JLabel("Cadastrar funcionario"));
        BInserir = new JButton("Cadastrar");
        BInserir.addActionListener(new Insere());
        Campos[0].add(BInserir);
        Campos[0].add(new JLabel("Cargo"));
        InsCargo = new JTextArea();
        InsCargo.setBorder(borda);
        Campos[0].add(InsCargo);
        Campos[0].add(new JLabel("Nome"));
        InsNome = new JTextArea();
        InsNome.setBorder(borda);
        Campos[0].add(InsNome);
        Campos[0].add(new JLabel("Salario"));
        InsSalario = new JTextArea();
        InsSalario.setBorder(borda);
        Campos[0].add(InsSalario);
        Campos[0].add(new JLabel("Descricao"));
        InsDescricao = new JTextArea();
        InsDescricao.setBorder(borda);
        Campos[0].add(InsDescricao);

        Campos[1].setLayout(new GridLayout(6, 2));
        Campos[1].add(new JLabel("Alterar cadastro"));
        BAlterar = new JButton("Alterar");
        BAlterar.addActionListener(new Altera());
        Campos[1].add(BAlterar);
        Campos[1].add(new JLabel("Nome do funcionario cujo cadastro sera alterado"));
        AltID = new JTextArea();
        AltID.setBorder(borda);
        Campos[1].add(AltID);
        Campos[1].add(new JLabel("Novo cargo"));
        AltCargo = new JTextArea();
        AltCargo.setBorder(borda);
        Campos[1].add(AltCargo);
        Campos[1].add(new JLabel("Novo nome"));
        AltNome = new JTextArea();
        AltNome.setBorder(borda);
        Campos[1].add(AltNome);
        Campos[1].add(new JLabel("Novo salario"));
        AltSalario = new JTextArea();
        AltSalario.setBorder(borda);
        Campos[1].add(AltSalario);
        Campos[1].add(new JLabel("Nova descricao"));
        AltDescricao = new JTextArea();
        AltDescricao.setBorder(borda);
        Campos[1].add(AltDescricao);

        Campos[2].setLayout(new GridLayout(2, 2));
        Campos[2].add(new JLabel("Excluir cadastro"));
        BExcluir = new JButton("Excluir");
        BExcluir.addActionListener(new Exclui());
        Campos[2].add(BExcluir);
        Campos[2].add(new JLabel("Nome do funcionario cujo cadastro sera excluido"));
        ExcID = new JTextArea();
        ExcID.setBorder(borda);
        Campos[2].add(ExcID);

        Tabela = new JTable(new Object[1][5], new String[] {"ID", "Cargo", "Nome", "Salario", "Descricao"});
        Campos[3].setLayout(new GridLayout(2, 1));
        Campos[3].add(Tabela.getTableHeader());
        Campos[3].add(Tabela);

        for (int i = 0; i < 4; i++) Campos[i].setBorder(borda);
        AtualizaJanela();
        CriaJanela(Campos);
    }

    public void AtualizaJanela() {
        DefaultTableModel Modelo = new DefaultTableModel(new Object[] {"Cargo", "Nome", "Salario", "Descricao"}, 0);
        try {
            ResultSet Dados = Afirmacao.executeQuery("SELECT * FROM FUNCIONARIOS");
            while (Dados.next()) {
                String Cargo = Dados.getString("CARGO");
                String Nome = Dados.getString("NOME");
                float Salario = Dados.getFloat("SALARIO");
                String Descricao = Dados.getString("DESCRICAO");
                Modelo.addRow(new Object[] {Cargo, Nome, Salario, Descricao});
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
            String Cargo = InsCargo.getText();
            String salario = InsSalario.getText();
            String Descricao = InsDescricao.getText();
            if (Cargo.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um cargo.");
                return;
            }
            if (Cargo.length() > 25) {
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
            if (salario.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um salario.");
                return;
            }
            float Salario;
            try {
                Salario = Float.parseFloat(salario);
            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(null,"Insira um numero real como salario.");
                return;
            }
            if (Descricao.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira uma descricao.");
                return;
            }
            if (Descricao.length() > 100) {
                JOptionPane.showMessageDialog(null,"Insira uma descricao com menos de 100 caracteres.");
                return;
            }
            try {
                Afirmacao.executeUpdate("INSERT INTO FUNCIONARIOS VALUES('"+ Cargo +"', '"+ Nome +"', "+ Salario +", '"+ Descricao +"')");
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
            String Cargo = AltCargo.getText();
            String Nome = AltNome.getText();
            String salario = AltSalario.getText();
            String Descricao = AltDescricao.getText();
            if (NomeEspecificado.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um nome como especificacao.");
                return;
            }
            if (NomeEspecificado.length() > 50) {
                JOptionPane.showMessageDialog(null,"Insira um nome com 50 caracteres ou menos como especificacao.");
                return;
            }
            if (Cargo.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um cargo.");
                return;
            }
            if (Cargo.length() > 25) {
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
            if (salario.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira um salario.");
                return;
            }
            float Salario;
            try {
                Salario = Float.parseFloat(salario);
            } catch (NumberFormatException erro) {
                JOptionPane.showMessageDialog(null,"Insira um numero real como salario.");
                return;
            }
            if (Descricao.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Insira uma descricao.");
                return;
            }
            if (Descricao.length() > 100) {
                JOptionPane.showMessageDialog(null,"Insira uma descricao com menos de 100 caracteres.");
                return;
            }
            try {
                Afirmacao.executeUpdate("UPDATE FUNCIONARIOS SET CARGO='"+ Cargo +"', NOME='"+ Nome +"', SALARIO="+ Salario +", DESCRICAO='"+ Descricao +"' WHERE NOME='"+ NomeEspecificado +"'");
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
                Afirmacao.executeUpdate("DELETE FROM FUNCIONARIOS WHERE NOME='"+ Nome +"'");
            } catch(SQLException erro) {
                erro.printStackTrace();
                System.exit(1);
            }
            AtualizaJanela();
        }
    }
}