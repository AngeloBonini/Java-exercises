
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
 
public class MulticastSocketServer {
    static String INET_ADDR = "224.0.0.3";
    static int PORT = 8888; // servidor porta 1
    static int PORT2 = 5000;// cliente porta 1
    static int PORT1 = 5001;// cliente porta 2
    static int serverport2=8889;// servidor porta 2
    InetAddress addr = null;
    public final Runnable servcliente1;
    public final Runnable servcliente2;
    
    public MulticastSocketServer (int PORT, int serverport2, String INET_ADDR, int PORT1, int PORT2) {
		this.PORT = PORT;// porta para enviar dados para o primeiro cliente
		this.INET_ADDR = INET_ADDR;//endereço que os servidor vai trabalhar
		try {
			addr = InetAddress.getByName(INET_ADDR);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.PORT1 = PORT1;// porta de recebimento do primeiro cliente
		this.PORT2 = PORT2;// porta de recebimento do segundo cliente
		this.serverport2 = serverport2;// porta para enviar dados para o segundo cliente
		servcliente1 = new Runnable() {
            public void run() {
                MulticastSocket cliente1 = null;
    	        MulticastSocket envia1 = null;
    	        try {
    			    envia1 = new MulticastSocket();// cria um multicast para enviar dados para os clientes
    			} catch (IOException e2) {
    				// TODO Auto-generated catch block
    				e2.printStackTrace();
    			}
    			try {
    				 cliente1 = new MulticastSocket(PORT1);// multicast para receber dados do cliente 1
    			} catch (IOException e1) {
    				e1.printStackTrace();
    			}
    	        try {
    				cliente1.joinGroup(InetAddress.getByName(INET_ADDR));//se junta ao endereço 
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    	        byte buf1[] = new byte[1024];// cria um buffer
    	        while(true) {
    	        	 DatagramPacket pack = new DatagramPacket(buf1, buf1.length);//cria pacote para receber
    	             try {
    					cliente1.receive(pack);//recebe string cliente1
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    	             String receivedMessageclient1 = new String(pack.getData());//transforma os dados do cliente 1 para string
    	 			 if(receivedMessageclient1.equals("fecha"))
    	 				 break;
    	             System.out.println("(servidor/recebendo)  " + receivedMessageclient1 );
    	             DatagramPacket msgPacket = new DatagramPacket(receivedMessageclient1.getBytes(),
    	             receivedMessageclient1.getBytes().length, addr, serverport2); // transforma a string do cliente 1 um em pacote e envia para a porta selecionada( cliente2)
    			     try {
    					envia1.send(msgPacket);// envia
    				} catch (IOException e1) {
    					e1.printStackTrace();
    				}
    			      
    		}
    	        try {
    	        	cliente1.leaveGroup(InetAddress.getByName(INET_ADDR));
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    	        cliente1.close();
    	        envia1.close();
        	
            }
        };
        servcliente2 = new Runnable() {
            public void run() {
            	 MulticastSocket cliente2 = null;
     	        MulticastSocket envia2 = null;
     	        try {
     			    envia2 = new MulticastSocket();// cria um multicast para enviar dados para os clientes
     			} catch (IOException e2) {
     				// TODO Auto-generated catch block
     				e2.printStackTrace();
     			}
     			try {
     				cliente2 = new MulticastSocket(PORT2);// multicast para receber dados do cliente 2
     			} catch (IOException e1) {
     				e1.printStackTrace();
     			}
     	        try {
     				cliente2.joinGroup(InetAddress.getByName(INET_ADDR));// se junta ao endereço
     			} catch (IOException e) {
     				e.printStackTrace();
     			}
     	        byte buf2[] = new byte[1024];// cria um buffer
     	        while(true) {
     	 			DatagramPacket pack1 = new DatagramPacket(buf2, buf2.length);//cria um pacote de dados
     	             try {
     					cliente2.receive(pack1); // recebe string cliente2
     				} catch (IOException e) {
     					e.printStackTrace();
     				}
     	             String receivedMessageclient2 = new String(pack1.getData());// transforma os dados  do cliente 2 em string
     	            if(receivedMessageclient2.equals("fecha"))
   	 				 break;
     	             System.out.println("(servidor/recebendo2)  " + receivedMessageclient2 );
     	             DatagramPacket msgPacket1 = new DatagramPacket(receivedMessageclient2.getBytes(),
     	             receivedMessageclient2.getBytes().length, addr, PORT); // transforma a string do cliente 2 e envia para a porta selecionada
     	             try {
     					envia2.send(msgPacket1);//envia
     				} catch (IOException e) {
     					e.printStackTrace();
     				}
     	 	  }	
     	       try {
   	        	cliente2.leaveGroup(InetAddress.getByName(INET_ADDR));
   			} catch (IOException e) {
   				e.printStackTrace();
   			}
   	        cliente2.close();
   	        envia2.close();
            }
        };

    }
    
    
    
    
    
    	
    
    
    
    
    
    
    
    
    /*
    public void run() {
        InetAddress addr = null;
		try {
			addr = InetAddress.getByName(INET_ADDR);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        MulticastSocket cliente1 = null;
        MulticastSocket cliente2 = null;
        MulticastSocket envia = null;
        try {
		    envia = new MulticastSocket();// cria um multicast para enviar dados para os clientes
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			cliente2 = new MulticastSocket(PORT2);// multicast para receber dados do cliente 2
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			 cliente1 = new MulticastSocket(PORT1);// multicast para receber dados do cliente 1
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        try {
			cliente1.joinGroup(InetAddress.getByName(INET_ADDR));//se junta ao endereço 
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			cliente2.joinGroup(InetAddress.getByName(INET_ADDR));// se junta ao endereço
		} catch (IOException e) {
			e.printStackTrace();
		}
        byte buf[] = new byte[1024];// cria um buffer
        while(true) {
        	 try {
 		         Thread.sleep(10);
 		      }catch(InterruptedException ex) {
 		        Thread.currentThread().interrupt();
 		    }
        	 DatagramPacket pack = new DatagramPacket(buf, buf.length);//cria pacote para receber
             try {
				cliente1.receive(pack);//recebe string cliente1
			} catch (IOException e) {
				e.printStackTrace();
			}
             String receivedMessageclient1 = new String(pack.getData());//transforma os dados do cliente 1 para string
 			 System.out.println("(servidor/recebendo)  " + receivedMessageclient1 );
             DatagramPacket msgPacket = new DatagramPacket(receivedMessageclient1.getBytes(),
             receivedMessageclient1.getBytes().length, addr, serverport2); // transforma a string do cliente 1 um em pacote e envia para a porta selecionada( cliente2)
		     try {
				envia.send(msgPacket);// envia
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		     try {
 		         Thread.sleep(10);
 		      }catch(InterruptedException ex) {
 		        Thread.currentThread().interrupt();
 		    }
 			DatagramPacket pack1 = new DatagramPacket(buf, buf.length);//cria um pacote de dados
             try {
				cliente2.receive(pack1); // recebe string cliente2
			} catch (IOException e) {
				e.printStackTrace();
			}
             String receivedMessageclient2 = new String(pack1.getData());// transforma os dados  do cliente 2 em string
 			 System.out.println("(servidor/recebendo2)  " + receivedMessageclient2 );
             DatagramPacket msgPacket1 = new DatagramPacket(receivedMessageclient2.getBytes(),
             receivedMessageclient2.getBytes().length, addr, PORT); // transforma a string do cliente 2 e envia para a porta selecionada
             try {
				envia.send(msgPacket1);//envia
			} catch (IOException e) {
				e.printStackTrace();
			}
 	  }
    }
    
    */
    
    public static void main(String[] args) throws InterruptedException, IOException {
    	
	}
    

}
