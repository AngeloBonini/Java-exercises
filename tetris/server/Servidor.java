import java.awt.Rectangle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int porto = 55055;
    
        try {
          serverSocket = new ServerSocket(porto);
        } catch (IOException e) {
          System.out.println("Nao pode escutaro o porto: " + porto + ".\n" + e);
          System.exit(1);
        }
    
        while (true) {
          IJogo jogo = new Jogo();
          int numMaximoJogadores = jogo.numMaximoJogadores();
          for (int i = 0; i < numMaximoJogadores; i++) {
            Socket clientSocket = null;
            try {
              System.out.println("Esperando conexao de um jogador.");
              clientSocket = serverSocket.accept();
            } catch (IOException e) {
              System.out.println("Accept falhou: " + porto + ".\n" + e);
              System.exit(1);
            }
            System.out.println("Accept Funcionou!");
            jogo.adicionaJogador(clientSocket);
          }
          jogo.iniciaLogica(new Logica(jogo));
          jogo.inicia();
        }
      } 
}

class Jogo extends Thread implements IJogo {
    Socket client;
    DataOutputStream[] osList = new DataOutputStream[2];
    long scoreTabuleiro1=0;
    long scoreTabuleiro2=0;
    int origemPedacoTabuleiro1X;
    int origemPedacoTabuleiro1Y;
    int origemPedacoTabuleiro2X;
    int origemPedacoTabuleiro2Y;

    int rotacaoTabuleiro1;
    int rotacaoTabuleiro2;


    int jogadoresConectados =0;
    public void run(){
        int numeroJogador = 0;
        try{

        }
    }
}


class Logica implements ILogica{
    Jogo jogo;

    Logica(IJogo jogo){
        this.jogo = (Jogo) jogo;
    }

    public void executa(){
        limitesTabuleiro1();
        enviaPedaco1();
    }
}