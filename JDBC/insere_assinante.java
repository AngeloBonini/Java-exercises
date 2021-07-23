import java.sql.*;

public class insere_assinante {

    public static void main(String[] args) {
    try {
    Class.forName("org.hsql.jdbcDriver");
    Connection con =
    DriverManager.getConnection("jdbc:HypersonicSQL:hsql://localhost:8080", "sa", "");
    Statement stmt = con.createStatement();
    stmt.executeUpdate("insert into alunos values (23452342340, 'Oswaldo', 'Rua Sacoman ','6500 4513 9725 4803', 'basico')");
    stmt.executeUpdate("insert into alunos values (3242343243243241, 'Marco Antonio', 'Avenida Brasil', '6500 4513 9725 4803','premium')");
    stmt.executeUpdate("insert into alunos values (3243242343232, 'Joao Eduardo', 'Avenida das Nações Unidas','6500 4513 9725 4803', 'medio')");
    stmt.executeUpdate("insert into alunos values (645646456673, 'Andrea Cristina', 'Rua Edemilson da Cruz Pinheiro','6500 4513 9725 4803', 'plano de teste')");
    stmt.executeUpdate("insert into alunos values (645646456673, 'Adriana Vanessa', 'Alameda Doutor Elias Vicente','6500 4513 9725 4803', 'plano de teste')");
    stmt.close();
    con.close();
    } catch (Exception e) {
    System.out.println(e);
    }
    }
}
