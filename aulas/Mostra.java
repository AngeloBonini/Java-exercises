interface MostraDados{

    Integer l
    Float f

    mostra(String[] in){
System.out.println(in);
    }
}



public class Mostra extends MostraDados {
    public static void main(String[] args){
    System.out.println(args)
   }
  
}
