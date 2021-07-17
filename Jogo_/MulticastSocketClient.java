
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
public class MulticastSocketClient implements Runnable {
    static String INET_ADDR;
    static int PORT;
    static int PORTcliente;
	static String name = new String();
	private String address;
    private int controle;
   
    public MulticastSocketClient (String name, String INET_ADDR, int PORTcliente, int PORT, int controle) {
		this.name = name; //nome do jogo 
		this.INET_ADDR = INET_ADDR; // endereço inet
		this.PORTcliente = PORTcliente; // Porta para enviar dados para o servidor 
		this.PORT = PORT; // porta para receber do servidor
		this.controle = controle;// escolha do player
	}
	
    
    public void run() {
    	jogo game = new jogo(name, controle);// cria jogo
    	game.start();// inicia o jogo
        InetAddress address = null;// inicia endereço
        DatagramPacket msgPacket= null;//inicia pacote para inserir dados e enviar para o servidor
		try {
			address = InetAddress.getByName(INET_ADDR);// atribui o endereço para a variavel
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        byte[] buf = new byte[256]; // cria buffer de dados
       MulticastSocket clientSocket = null; // multicastsocket para receber dados do servidor
       MulticastSocket enviaSocket = null;  // multicastsocket para enviar dados para o servidor
       try {
		enviaSocket = new MulticastSocket();// inicia a variavel
	} catch (IOException e2) {
		e2.printStackTrace();
	}
	try {
		clientSocket = new MulticastSocket(PORT);// inicia a variavel e atribui a porta de recebimento do servidor
	} catch (IOException e1) {
		e1.printStackTrace();
	}
            try {
				clientSocket.joinGroup(address);// se junta ao "grupo" do endereço(serve para varios clientes receber o mesmo dado)
			} catch (IOException e) {
				e.printStackTrace();
			}
            while (!jogo.fecha) {
            	String message = game.sendnudes();// atribui as variaveis à string message
                msgPacket = null;
                msgPacket = new DatagramPacket(
    					message.getBytes(),
    					message.length(), address, PORTcliente
    					);// o datagram recebe a string com o dado da porta que é para enviar
                try {
					enviaSocket.send(msgPacket);// envia para o servidor
				} catch (IOException e) {
					e.printStackTrace();
				} 
                msgPacket = null;//zera o datagramPackeg
                msgPacket = new DatagramPacket(buf, buf.length);//atribui tamanho a ele
                try {
					clientSocket.receive(msgPacket);//recebe do servidor atraves da porta
				} catch (IOException e) {
					e.printStackTrace();
				}
                String msg = new String(buf, 0, buf.length); // atribui os dados recebidos para uma string
                game.receivenudes(msg);// atualiza os dados no jogo
                try {
      		         Thread.sleep(10);
      		      }catch(InterruptedException ex) {
      		        Thread.currentThread().interrupt();
      		    }
                System.out.println("(client/recebendo) Socket 1 received msg: " + msg + ("\n"));
              
                
            }
            String message = "fecha";// atribui as variaveis à string message
            msgPacket = null;
            msgPacket = new DatagramPacket(
					message.getBytes(),
					message.length(), address, PORTcliente
					);// o datagram recebe a string com o dado da porta que é para enviar
            try {
				enviaSocket.send(msgPacket);// envia para o servidor
			} catch (IOException e) {
				e.printStackTrace();
			} 
            try {
				clientSocket.leaveGroup(address);
			} catch (IOException e) {
				e.printStackTrace();
			}
            clientSocket.close();
            enviaSocket.close();
    }
    
    
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
       
    }
}
