import java.sql.*;

public class tabela_assinantes {
    public static void main(String[] args) {
        try {
            Class.forName("org.hsql.jdbcDriver");
Connection con =
DriverManager.getConnection("jdbc:HypersonicSQL:hsql://localhost:8080", "sa", "");
Statement stmt = con.createStatement();
stmt.executeUpdate("create tabela_assinantes (id int, nome varchar(20), endereco varchar(30),cartao_credito varchar(30), plano varchar(20))");
stmt.close();
con.close();
} catch (Exception e) {
System.out.println(e);
        }
    }
    
}
