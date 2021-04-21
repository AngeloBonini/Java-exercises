class DivisaoPorZeroExcpt extends RuntimeException {
    public String toString() {
      return "Erro de divisao por zero!";
    }
  }
  
  public class MilPorN {
    double valor;
  
    MilPorN(double n) throws DivisaoPorZeroExcpt {
      if (n == 0) {
        throw new DivisaoPorZeroExcpt();
      }
      valor = 1000 / n;
    }
    
    public String toString() {
      return "Resultado: " + valor;
    }
  }