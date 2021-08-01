//java -classpath .:hsql.jar SistemaLoja

public class SistemaLoja {
    Musicas tpedidos;
    Filmes tFilmes;
    Assinantes tassinantes;

    SistemaLoja() {
        tpedidos = new Musicas();
        tFilmes = new Filmes();
        tassinantes = new Assinantes();
    }

    public static void main(String[] args) {
        SistemaLoja p = new SistemaLoja();
    }
}