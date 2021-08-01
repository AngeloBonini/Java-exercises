//java -classpath .:hsql.jar SistemaLoja

public class StreamingDatabase {
    Musicas tablesMusicas;
    Filmes tabelaFilmes;
    Assinantes tabelaAssinantes;

    StreamingDatabase() {
        tablesMusicas = new Musicas();
        tabelaFilmes = new Filmes();
        tabelaAssinantes = new Assinantes();
    }

    public static void main(String[] args) {
        StreamingDatabase geloFlix = new StreamingDatabase();
    }
}