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


    int rotacaoTabuleiro1=0;
    int rotacaoTabuleiro2=0;


    int jogadoresConectados =0;
    public void run(){
        int numeroJogador = 0;
     
    }

    public int numMaximoJogadores(){
      return 2;
    }

    public void iniciaLogica(ILogica logica){
      this.logica = logica;
    }

    public void inicia(){
      start();
    }
}


class Logica implements ILogica{
    Jogo jogo;
    int coordX_tab1 = 5;
    int coordY_tab = 2;
    int coordX_tab2 = 17;


    Logica(IJogo jogo){
        this.jogo = (Jogo) jogo;
    }

    public void executa(){
        limitesTabuleiro1();
        enviaPedaco1X();
        enviaPedaco2X();
        enviaPedacoY();
    }


    public int limitesTabuleiro1(){

    }

    public int limitesTabuleiro2(){

    }

   public int  enviaPedaco1X(){
          return coordX_tab1;
    }
    
    public int enviaPedaco2X(){
          return coordX_tab2;
     }
    public int enviaPedacoY(){
          return coordY_tab;
    }

}