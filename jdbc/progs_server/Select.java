import java.sql.*;

class Select {
  public static void main(String[] args) {
    try {
      Class.forName("org.hsql.jdbcDriver");
      Connection con = DriverManager.getConnection("jdbc:HypersonicSQL:hsql://localhost:8080", "sa", "");
      Statement stmt = con.createStatement();
//      ResultSet rs = stmt.executeQuery("SELECT * FROM AGENDA");
      ResultSet rs = stmt.executeQuery("SELECT * FROM AGENDA WHERE NOME LIKE 'JO%'");
      while (rs.next()) {
        String s = rs.getString("NOME");
        float n = rs.getFloat("TELEFONE");
        System.out.println(s + "   " + n);
      }
      stmt.close();
      con.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
