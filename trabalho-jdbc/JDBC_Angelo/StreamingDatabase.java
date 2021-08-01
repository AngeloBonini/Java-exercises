

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

/**
 * Instruções:
 * 
 * compile com javac *.java 
 * depois rode: java -classpath .:hsql.jar  StreamingDatabase
 */