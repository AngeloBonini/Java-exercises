import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
  
public class MDI extends JFrame implements ActionListener {  
  Connection con;
  Statement stmt; 
  JDesktopPane desktop;
        
  JMenuItem menuCriar;
  JMenuItem menuInsere;
  JMenuItem menuConsulta;
  JMenuItem menuTermina;
  
  JanelaConsulta janelaConsulta;
  
  public MDI() {  
      super("Multiple Document Interface");  
        
      setBounds(50,50,700,500);  
      setExtendedState(JFrame.MAXIMIZED_BOTH);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        
      desktop = new JDesktopPane();  
      add(desktop);  
        
      setJMenuBar(criaMenu());
      
      iniciaBD();
      
      janelaConsulta = new JanelaConsulta(desktop, con);  
      desktop.add(janelaConsulta);  
      janelaConsulta.setVisible(false);  
    
      setVisible(true);  
  }  
  
  JMenuBar criaMenu() {
    JMenuBar menuBar = new JMenuBar();
      JMenu menuBD = new JMenu("Banco de Bados");
      menuBar.add(menuBD);
        menuCriar = new JMenuItem("Criar Tabela");
        menuBD.add(menuCriar);
        menuInsere = new JMenuItem("Insere Dados", KeyEvent.VK_I);
        menuBD.add(menuInsere);
        menuConsulta = new JMenuItem("Consulta Tabela", KeyEvent.VK_C);
        menuBD.add(menuConsulta);
      menuTermina = new JMenuItem("Termina");
      menuBar.add(menuTermina);
      
      menuCriar.addActionListener(this);
      menuInsere.addActionListener(this);
      menuConsulta.addActionListener(this);
      menuTermina.addActionListener(this);
    return menuBar;
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == menuCriar) {
      try {
        stmt.executeUpdate("CREATE TABLE AGENDA (NOME VARCHAR(30), TELEFONE INTEGER)");
        JOptionPane.showMessageDialog(null, "Tabela criada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
      } catch (SQLException ex) {
        JOptionPane.showMessageDialog(desktop, "Erro na criação da tabela.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
      } catch (NullPointerException ex) {
        JOptionPane.showMessageDialog(desktop, "Problema interno.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
      }
    } else if (e.getSource() == menuInsere) {
      new JanelaInsere(desktop, con);
    } else if (e.getSource() == menuConsulta) {
      janelaConsulta.setVisible(true);
    } else if (e.getSource() == menuTermina) {
      System.exit(0);
    }
  }
  
  void iniciaBD() {
    try {
      Class.forName("org.hsql.jdbcDriver");
      con = DriverManager.getConnection("jdbc:HypersonicSQL:http://127.0.0.1", "sa", "");
      stmt = con.createStatement();
    } catch (ClassNotFoundException ex) {
      JOptionPane.showMessageDialog(null, "O driver do banco de dados não foi encontrado.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Erro na iniciação do acesso ao banco de dados\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }
  }
  
  public void finalize() {
    try {
      stmt.close();
      con.close();
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(desktop, "Problema interno.\n"+e, "Erro", JOptionPane.ERROR_MESSAGE);
    }
  }
    
  public static void main(String[] args ) {  
      new MDI();  
  }  
}  

class JanelaInsere extends JInternalFrame {
  PreparedStatement pStmt;
  JDesktopPane desktop;
  JButton bt1;
  JTextField tf1, tf2;

  public JanelaInsere(JDesktopPane d, Connection con) {
    super("Insere na tabela AGENDA", false, true, false, true); //resizable, closable, maximizable, iconifiable
    desktop = d;
    try {
      pStmt = con.prepareStatement("INSERT INTO AGENDA VALUES (?, ?)");

      setLayout(new FlowLayout());
      add(new JLabel("Nome: "));
      add(tf1 = new JTextField(30));
      add(new JLabel("Tel: "));
      add(tf2 = new JTextField(10));
      add(bt1 = new JButton("Insere"));
      pack();
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setVisible(true);
      desktop.add(this);

      bt1.addActionListener(new ActionListener() {    //classe interna listener sem nome
        public void actionPerformed(ActionEvent e) {
          try {
            pStmt.setString(1, tf1.getText());
            pStmt.setInt(2, Integer.parseInt(tf2.getText()));
            tf1.setText("");
            tf2.setText("");
            pStmt.executeUpdate();
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(desktop, "Problema interno.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
          }
        }
      });

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(desktop, "Problema interno.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public void finalize() {
    try {
      pStmt.close();
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "Problema interno.\n"+e, "Erro", JOptionPane.ERROR_MESSAGE);
    }
  }
}

class JanelaConsulta extends JInternalFrame implements ActionListener {
  PreparedStatement pStmt;
  JDesktopPane desktop;
  JButton bt1;
  JTextField tf1;
  JTextArea ta1;

  public JanelaConsulta(JDesktopPane d, Connection con) {
    super("Consulta na tabela AGENDA", false, true, false, true); //resizable, closable, maximizable, iconifiable
    desktop = d;
    try {
      pStmt = con.prepareStatement("SELECT * FROM AGENDA WHERE NOME LIKE ?");

      JPanel l1 = new JPanel();
      l1.add(new JLabel("Nome: "));
      l1.add(tf1 = new JTextField(30));
      l1.add(bt1 = new JButton("Pesquisa"));
      add(l1, BorderLayout.NORTH);
      l1 = new JPanel();
      JScrollPane scrollPane = new JScrollPane(ta1 = new JTextArea(5, 30));
      l1.add(scrollPane);
      add(l1, BorderLayout.CENTER);

      bt1.addActionListener(this);
      pack();
      setDefaultCloseOperation(HIDE_ON_CLOSE);

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(desktop, "Problema interno.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public void actionPerformed(ActionEvent e) {
    try {
      ta1.setText("");
      pStmt.setString(1, tf1.getText());
      ResultSet rs = pStmt.executeQuery();
      while (rs.next()) {
        String s = rs.getString(1);
        String n = rs.getString(2);
        ta1.append(s + "   " + n + "\n");
      }
      tf1.selectAll();
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(desktop, "Problema interno.\n"+ex, "Erro", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public void finalize() {
    try {
      pStmt.close();
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(desktop, "Problema interno.\n"+e, "Erro", JOptionPane.ERROR_MESSAGE);
    }
  }
}
