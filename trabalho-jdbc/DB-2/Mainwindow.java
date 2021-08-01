import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

/* Database:
 * -----------  
 *  Table Name: Alunos
 * -----------
 * Primary Key: id
 * Field 1: id
 * Field 2: name
 * Field 3: course
 * Field 4: period 
 * -----------
 * ID INT NOT NULL, NAME VARCHAR(50) NOT NULL, COURSE VARCHAR(30) NOT NULL, PERIOD VARCHAR(30)
 */
class Mainwindow extends JFrame implements ActionListener {
  // Components for the database
  public Connection con;
  private Statement stmt;

  private JPanel containerViewDatabase = new JPanel(new FlowLayout());
  private JPanel containerViewTable = new JPanel();

  private JPanel Pfields3 = new JPanel(new GridLayout(4, 2, 0, 5));
  private JPanel Pfields2 = new JPanel(new GridLayout(4, 2, 0, 5));
  private JPanel Pfields = new JPanel(new GridLayout(4, 2, 0, 5));
  private JPanel Pbuttons = new JPanel(new FlowLayout());

  private JButton add = new JButton("Add");
  private JButton delete = new JButton("Remove");
  private JButton search = new JButton("Update");
  private JButton close = new JButton("Close");

  // Init Database
  void initDb() {
    try {
      Class.forName("org.hsql.jdbcDriver");
      con = DriverManager.getConnection("jdbc:HypersonicSQL:bd_alunos", "sa", "");
      stmt = con.createStatement();
    } catch (ClassNotFoundException e1) {
      JOptionPane.showMessageDialog(null, "The database driver was not found.\n" + e1, "Error",
          JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "Error in the initiation of access to the database\n" + e1, "Error",
          JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }
  }

  // Exit Database
  public void finalize() {
    try {
      stmt.close();
      con.close();
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "Internal problem.\n" + e1, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // ActionListener for buttons
  public void actionPerformed(ActionEvent e) {
    Mainwindow Main_window = this;

    new Thread(new Runnable() {
      public void run() {
        if (e.getSource().equals(search)) {
          Main_window.setVisible(false);
          System.out.println("+ Creating Search Window in the Database");

          new SearchWindow(null, con); // Opens search window (modal)
        } else if (e.getSource().equals(add)) {
          Main_window.setVisible(false);
          System.out.println("+ Creating Insert Window in the Database");

          new AddWindow(null, con); // Opens add window (modal)
        } else if (e.getSource().equals(delete)) {
          Main_window.setVisible(false);
          System.out.println("+ Creating data removal window in the database");

          new SearchWindow(null, con); // Opens delete window (modal)
        } else if (e.getSource().equals(close)) {
          System.out.println("- Closing the Database...");
          try {
            Thread.sleep(500);
          } catch (InterruptedException e1) {
            System.out.println("Error: " + e1);
          }

          System.exit(0);
        }
        Main_window.setVisible(true); // Main Window is visible
      }
    }).start();
  }

  Mainwindow() {
    super("School data management");
    // change font
    setFont(new Font("Comic Sans Ms", Font.BOLD, 14));

    // Actions
    search.addActionListener(this);
    add.addActionListener(this);
    delete.addActionListener(this);
    close.addActionListener(this);

    // Button panels
    Pbuttons.add(add);
    Pbuttons.add(search);
    Pbuttons.add(delete);
    Pbuttons.add(close);

    Pbuttons.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
        "Database Management Options"));

    // Field panels
    // Alunos
    Pfields.add(new JLabel("id"));
    Pfields.add(new JLabel("int [not null]"));
    Pfields.add(new JLabel("name"));
    Pfields.add(new JLabel("varchar(50) [not null]"));
    Pfields.add(new JLabel("course"));
    Pfields.add(new JLabel("varchar(50) [not null]"));
    Pfields.add(new JLabel("period"));
    Pfields.add(new JLabel("varchar(30)"));
    // Professor
    Pfields2.add(new JLabel("id"));
    Pfields2.add(new JLabel("int [not null]"));
    Pfields2.add(new JLabel("name"));
    Pfields2.add(new JLabel("varchar(50) [not null]"));
    Pfields2.add(new JLabel("department"));
    Pfields2.add(new JLabel("varchar(50) [not null]"));
    Pfields2.add(new JLabel("wage"));
    Pfields2.add(new JLabel("int [not null]"));
    // Salas
    Pfields3.add(new JLabel("id"));
    Pfields3.add(new JLabel("int [not null]"));
    Pfields3.add(new JLabel("description"));
    Pfields3.add(new JLabel("varchar(30) [not null]"));
    Pfields3.add(new JLabel("capacity"));
    Pfields3.add(new JLabel("int [not null]"));

    Pfields.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Alunos",
        TitledBorder.CENTER, 0, new Font("Comic Sans Ms", Font.BOLD, 14)));
    Pfields2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
        "Professor", TitledBorder.CENTER, 0, new Font("Comic Sans Ms", Font.BOLD, 14)));
    Pfields3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Salas",
        TitledBorder.CENTER, 0, new Font("Comic Sans Ms", Font.BOLD, 14)));

    containerViewTable.add(Pfields);
    containerViewTable.add(Pfields2);
    containerViewTable.add(Pfields3);

    containerViewTable
        .setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Tables",
            TitledBorder.CENTER, 0, new Font("Comic Sans Ms", Font.BOLD, 18)));
    containerViewDatabase.add(containerViewTable);
    containerViewDatabase.setBorder(BorderFactory
        .createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Viewing the database format"));

    add(Pbuttons, BorderLayout.NORTH);
    add(containerViewDatabase);

    // Init Database
    initDb();
    // Create Table if not exists
    try {
      stmt.executeUpdate(
          "CREATE TABLE ALUNOS (ID INT NOT NULL, NAME VARCHAR(50) NOT NULL, COURSE VARCHAR(50) NOT NULL, PERIOD VARCHAR(30))");
      stmt.executeUpdate(
          "CREATE TABLE PROFESSOR (ID INT NOT NULL, NAME VARCHAR(50) NOT NULL, DEPARTMENT VARCHAR(50) NOT NULL, WAGE INT NOT NULL)");
      stmt.executeUpdate(
          "CREATE TABLE SALAS (ID INT NOT NULL, DESCRIPTION VARCHAR(30) NOT NULL, CAPACITY INT NOT NULL)");
      JOptionPane.showMessageDialog(null, "Table created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e1) {
      JOptionPane.showMessageDialog(null, "Table already exists, so the database data has been restored.\n",
          "Information", JOptionPane.INFORMATION_MESSAGE);
    } catch (NullPointerException e1) {
      JOptionPane.showMessageDialog(null, "Internal problem.\n" + e1, "Error", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }

    pack();
    setResizable(false);
    this.setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    new Mainwindow();
  }
}

class AddWindow extends JDialog {
  // Elements of the dialog
  private JLabel nameLabel = new JLabel("Name:");
  private JTextField nameTextField = new JTextField(20);
  private JLabel enrollmentLabel = new JLabel("Enrollment:");
  private JTextField enrollmentTextField = new JTextField(20);
  private JLabel courseLabel = new JLabel("Course:");
  private JTextField courseTextField = new JTextField(20);
  private JLabel periodLabel = new JLabel("Period:");
  private JTextField periodTextField = new JTextField(20);

  private JButton okButton = new JButton("OK");
  private JButton cancelButton = new JButton("Cancel");

  private JPanel mainPanel = new JPanel();
  private JPanel panelInput = new JPanel();
  private JPanel panelButtons = new JPanel();

  // Conections
  private PreparedStatement pStmt;

  AddWindow(Frame owner, Connection con) {
    super(owner, "Add Window", true);

    AddWindow window = this;
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new Thread(new Runnable() {
          public void run() {
            if (e.getSource().equals(okButton)) {
              System.out.println("~ Adding new student...");
              try {
                pStmt = con.prepareStatement("INSERT INTO ALUNOS VALUES (?, ?, ?, ?)");
                pStmt.setInt(1, Integer.parseInt(enrollmentTextField.getText()));
                pStmt.setString(2, nameTextField.getText());
                pStmt.setString(3, courseTextField.getText());
                pStmt.setString(4, periodTextField.getText());
                pStmt.executeUpdate();
              } catch (SQLException ex) {
                System.out.println("Error adding student: " + ex.getMessage());
              }
              System.out.println("> Insertion completed!");
              window.dispose();
            }
          }
        }).start();
      }
    });
    cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new Thread(new Runnable() {
          public void run() {
            try {
              Thread.sleep(500);
              window.dispose();
            } catch (InterruptedException e1) {
              System.out.println("Error: " + e1);
            }
          }
        }).start();
      }
    });
    setContentPane(createContent());

    setSize(800, 300);
    setResizable(false);
    this.setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
  }

  private Container createContent() {

    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.add(panelInput);
    mainPanel.add(panelButtons);

    getContentPane().add(mainPanel);

    mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
        "Fill out the form to enter"));

    // Create the layout
    GroupLayout gLayout = new GroupLayout(panelInput);
    panelInput.setLayout(gLayout);
    gLayout.setAutoCreateGaps(true);
    gLayout.setAutoCreateContainerGaps(true);

    gLayout.setHorizontalGroup(gLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addGroup(gLayout.createSequentialGroup()
            .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(nameLabel)
                .addComponent(enrollmentLabel).addComponent(courseLabel).addComponent(periodLabel))
            .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(nameTextField, 10, 20, Short.MAX_VALUE)
                .addComponent(enrollmentTextField, 10, 20, Short.MAX_VALUE)
                .addComponent(courseTextField, 10, 20, Short.MAX_VALUE)
                .addComponent(periodTextField, 10, 20, Short.MAX_VALUE)))
        .addGroup(gLayout.createSequentialGroup().addComponent(okButton).addComponent(cancelButton)));

    gLayout.setVerticalGroup(gLayout.createSequentialGroup()
        .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(nameLabel)
            .addComponent(nameTextField, 10, 20, Short.MAX_VALUE))
        .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(enrollmentLabel)
            .addComponent(enrollmentTextField, 10, 20, Short.MAX_VALUE))
        .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(courseLabel)
            .addComponent(courseTextField, 10, 20, Short.MAX_VALUE))
        .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(periodLabel)
            .addComponent(periodTextField, 10, 20, Short.MAX_VALUE))
        .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(okButton)
            .addComponent(cancelButton)));

    return mainPanel;
  }

  public void finalize() {
    try {
      pStmt.close();
    } catch (SQLException e) {
      System.out.println("Error closing statement: " + e.getMessage());
    }
  }

  public static void main(String[] args, Connection connect) {
    new AddWindow(null, connect);
  }

  public void Action(ActionEvent e, Connection con) {
    AddWindow Thiswindow = this;
    new Thread(new Runnable() {
      public void run() {
        if (e.getSource().equals(okButton)) {
          System.out.println("~ Adding new student...");
          try {
            pStmt = con.prepareStatement("INSERT INTO ALUNOS VALUES (?, ?, ?, ?)");
            pStmt.setString(1, nameTextField.getText());
            pStmt.setInt(2, Integer.parseInt(enrollmentTextField.getText()));
            pStmt.setString(3, courseTextField.getText());
            pStmt.setString(4, periodTextField.getText());
            pStmt.executeUpdate();
          } catch (SQLException ex) {
            System.out.println("Error adding student: " + ex.getMessage());
          }
          Thiswindow.dispose();
        } else {
          try {
            Thread.sleep(500);
            Thiswindow.dispose();
          } catch (InterruptedException e1) {
            System.out.println("Error: " + e1);
          }
        }
      }
    }).start();
  }
}

class SearchWindow extends JDialog {

  private JPanel tempPanel = new JPanel();
  private JPanel tempPanel2 = new JPanel();
  private JPanel mainPanel2 = new JPanel();
  private JPanel mainPanel = new JPanel();

  private JPanel InputContainer = new JPanel(new FlowLayout(0, 10, 0));
  private JPanel QueryContainer = new JPanel(new FlowLayout(0, 10, 0));

  private ButtonGroup radioFieldAlunosGroup = new ButtonGroup();
  private ButtonGroup radioFieldProfessoresGroup = new ButtonGroup();
  private ButtonGroup radioFieldSalasGroup = new ButtonGroup();
  private ButtonGroup radioTableGroup = new ButtonGroup();

  // Data Tables
  private JRadioButton alunos = new JRadioButton("Students");
  private JRadioButton profressores = new JRadioButton("Teachers");
  private JRadioButton salas = new JRadioButton("Rooms");

  // Fields
  private JRadioButton name = new JRadioButton("Nome");
  private JRadioButton enrollment = new JRadioButton("Matrícula");
  private JRadioButton course = new JRadioButton("Curso");
  private JRadioButton period = new JRadioButton("Período");
  private JRadioButton departament = new JRadioButton("Departamento");
  private JRadioButton wage = new JRadioButton("Salário");
  private JRadioButton description = new JRadioButton("Description");
  private JRadioButton capacity = new JRadioButton("Capacity");

  private JTextField searchField = new JTextField(55);

  private JButton search = new JButton("Seek");
  private JButton delete = new JButton("Remove");
  private JButton update = new JButton("Update");
  private JButton confirm = new JButton("Confirm");

  private PreparedStatement pStmt;

  SearchWindow(Frame owner, Connection con) {
    super(owner, "Add Window", true);

    SearchWindow window = this;

    radioTableGroup.add(alunos);
    radioTableGroup.add(profressores);
    radioTableGroup.add(salas);

    radioTableGroup.setSelected(alunos.getModel(), true); // To test the conditional

    update.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new Thread(new Runnable() {
          public void run() {
            System.out.println("- Update Data...");
            try {
              pStmt = con.prepareStatement("UPDATE ? SET ? FROM ?");
              // pStmt.setInt(1, Integer.parseInt(enrollmentTextField.getText()));
              pStmt.executeUpdate();
            } catch (SQLException ex) {
              System.out.println("Error adding student: " + ex.getMessage());
            }
            System.out.println("> Update completed!");
            window.dispose();
          }
        }).start();
      }
    });
    delete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new Thread(new Runnable() {
          public void run() {
            System.out.println("- Removing from the database...");
            try {
              pStmt = con.prepareStatement("UPDATE ? SET ? FROM ?");
              // pStmt.setInt(1, Integer.parseInt(enrollmentTextField.getText()));
              pStmt.executeUpdate();
            } catch (SQLException ex) {
              System.out.println("Error adding student: " + ex.getMessage());
            }
            System.out.println("> Update completed!");
            window.dispose();
          }
        }).start();
      }
    });
    search.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new Thread(new Runnable() {
          public void run() {
            System.out.println("- Searching in the database...");
            try {
              pStmt = con.prepareStatement("SELECT * FROM ? WHERE ? LIKE ?");
              // pStmt.setInt(1, Integer.parseInt(enrollmentTextField.getText()));
              pStmt.executeUpdate();
            } catch (SQLException ex) {
              System.out.println("Error adding student: " + ex.getMessage());
            }
            System.out.println("> Update completed!");
            window.dispose();
          }
        }).start();
      }
    });
    confirm.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ButtonModel Selected_button = radioTableGroup.getSelection();
        System.out.println("- Confirm...");
        setContentPane(createContent(Selected_button));
      }
    });

    setContentPane(initialContentPane());
    // setContentPane(createContent());

    setSize(800, 300);
    setResizable(false);
    this.setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
  }

  // Creates table selection window
  private Container initialContentPane() {
    mainPanel2.setLayout(new BoxLayout(mainPanel2, BoxLayout.Y_AXIS));
    mainPanel2.add(tempPanel2);

    setContentPane(mainPanel2);

    mainPanel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    GroupLayout gLayout = new GroupLayout(tempPanel2);
    tempPanel2.setLayout(gLayout);
    gLayout.setAutoCreateGaps(true);
    gLayout.setAutoCreateContainerGaps(true);

    gLayout.setHorizontalGroup(gLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(confirm))
        .addGroup(gLayout.createSequentialGroup().addComponent(alunos).addComponent(profressores).addComponent(salas)));
    gLayout.setVerticalGroup(
        gLayout.createSequentialGroup().addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(alunos).addComponent(profressores).addComponent(salas)).addComponent(confirm));

    return mainPanel2;
  }

  // Creates window contents
  private Container createContent(ButtonModel Selected_button) {

    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.add(tempPanel);

    getContentPane().add(mainPanel);

    mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
        "Fill out the form to enter"));

    // Create the layout
    GroupLayout gLayout = new GroupLayout(tempPanel);
    tempPanel.setLayout(gLayout);
    gLayout.setAutoCreateGaps(true);
    gLayout.setAutoCreateContainerGaps(true);

    if (Selected_button != null) {
      if (Selected_button == alunos.getModel()) {

        radioFieldAlunosGroup.add(name);
        radioFieldAlunosGroup.add(enrollment);
        radioFieldAlunosGroup.add(course);
        radioFieldAlunosGroup.add(period);

        gLayout.setHorizontalGroup(gLayout
            .createSequentialGroup().addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(name).addComponent(enrollment).addComponent(course).addComponent(period))
            .addComponent(searchField));
        gLayout.setVerticalGroup(gLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(gLayout.createSequentialGroup()
                .addComponent(name).addComponent(enrollment).addComponent(course).addComponent(period))
            .addComponent(searchField));
      } else if (Selected_button == profressores.getModel()) {

        radioFieldProfessoresGroup.add(name);
        radioFieldProfessoresGroup.add(enrollment);
        radioFieldProfessoresGroup.add(departament);
        radioFieldProfessoresGroup.add(wage);

        gLayout.setHorizontalGroup(gLayout
            .createSequentialGroup().addGroup(gLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(name).addComponent(enrollment).addComponent(course).addComponent(period))
            .addComponent(searchField));
        gLayout.setVerticalGroup(gLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(gLayout.createSequentialGroup()
                .addComponent(name).addComponent(enrollment).addComponent(course).addComponent(period))
            .addComponent(searchField));
      }
    }
    return mainPanel;
  }

  public static void main(String[] args, Connection con) {
    new SearchWindow(null, con);
  }
}