//java -classpath .;hsql.jar SistemaLoja

public class SistemaLoja {
    Pedidos tpedidos;
    Filmes tFilmes;
    Assinantes tassinantes;

    SistemaLoja() {
        tpedidos = new Pedidos();
        tFilmes = new Filmes();
        tassinantes = new Assinantes();
    }

    public static void main(String[] args) {
        SistemaLoja p = new SistemaLoja();
    }
}