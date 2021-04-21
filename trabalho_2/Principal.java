public class Principal {

    public static void main (String[] args) {
      try {
        
        MilPorN milPor3 = new MilPorN(3);
        System.out.println(milPor3);
  
        MilPorN milPor0 = new MilPorN(0);
        System.out.println(milPor0);
        
      } catch (DivisaoPorZeroExcpt ex) {
        System.out.println("Erro: " + ex);
      } finally {
        System.out.println("Importante");
      }
  
      System.out.println("Fim!");
    }
  }
  