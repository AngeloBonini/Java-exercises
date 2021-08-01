//java -classpath .:hsql.jar SistemaLoja

public class StreamingDatabase {
    Musicas tpedidos;
    Filmes tFilmes;
    Assinantes tassinantes;

    StreamingDatabase() {
        tpedidos = new Musicas();
        tFilmes = new Filmes();
        tassinantes = new Assinantes();
    }

    public static void main(String[] args) {
        StreamingDatabase geloFlix = new StreamingDatabase();
    }
}