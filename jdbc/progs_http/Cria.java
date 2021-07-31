import java.sql.*;

class Cria {
  public static void main(String[] args) {
    try {
      Class.forName("org.hsql.jdbcDriver");
      Connection con = DriverManager.getConnection("jdbc:HypersonicSQL:http://localhost", "sa", ""); 
      Statement stmt = con.createStatement(); 
      stmt.executeUpdate("CREATE TABLE AGENDA " + 
						 "(NOME VARCHAR(30), TELEFONE INTEGER)"); 
      stmt.close();
      con.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
