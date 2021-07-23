import java.sql.*;
public class query_assinantes {
    
    public static void main(String[] args) {
    try {
    Class.forName("org.hsql.jdbcDriver");
    Connection con =
    DriverManager.getConnection("jdbc:HypersonicSQL:hsql://localhost:8080", "sa", "");
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("select * from tabela_assinantes order by id");
    while (rs.next()) {
    int id = rs.getInt("id");
    String name = rs.getString("nome");
    String address = rs.getString("endereco");
    String cartao = rs.getString("cartao_credito");
    String plano = rs.getString("plano");
    System.out.println("ID: " + id + " > Nome: " + name + " Endereço: " + address +
    " Número do Cartão: " + cartao + "Plano:" + plano );
    }
    stmt.close();
    con.close();
    } catch (Exception e) {
    System.out.println(e);
    }
    }
}
