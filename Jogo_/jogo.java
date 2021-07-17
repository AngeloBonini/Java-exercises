
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class jogo extends Canvas implements Runnable {
	
	boolean comeca = true;
	boolean comeca1= true;
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private BufferedImage background = null;
	private BufferedImage nave1 = null;
	private BufferedImage nave2 = null;
	private BufferedImage tiro1 = null;
	private BufferedImage tiro2 = null;
	private BufferedImage inicio = null;
	private BufferedImage escolha = null;
	private BufferedImage player1win = null;
	private BufferedImage player2win = null;
	public static BufferedImage[] coracao = new BufferedImage[6];
	public boolean disparo1 = false;
	public boolean disparo2 = false;
	boolean controlevindodecima;
	public boolean running = false;
	public int tickCount = 0;
	int Posy=500;
	int Posy2=500;
	int speed=0;
	int aux=0;
	int controle1 =60;
	int controle2 =60;
	Controllerbullet c = new Controllerbullet(tiro1);
	Controllerbullet2 c2 = new Controllerbullet2(tiro2);
	public static int coracrl = 2;
	public static int  coracrl2 = 3;
	String nudes = new String();
	String chegouosnudes = new String();
	public boolean desligadisparo1 = false;
	static boolean fecha= false;
	
	
	
	public void init() {
		try {
		    background = ImageIO.read(new File("src/Images/background1.jpg"));
		} catch (IOException e) {
		}
		try {
		    player1win = ImageIO.read(new File("src/Images/Player1win.jpg"));
		} catch (IOException e) {
		}
		try {
		    player2win = ImageIO.read(new File("src/Images/Player2win.jpg"));
		} catch (IOException e) {
		}
		try {
		    tiro2 = ImageIO.read(new File("src/Images/tirovermelho.jpg"));
		} catch (IOException e) {
		}
		try {
		    escolha = ImageIO.read(new File("src/Images/escolha.jpg"));
		} catch (IOException e) {
		}
		try {
		    inicio = ImageIO.read(new File("src/Images/inicio.jpg"));
		} catch (IOException e) {
		}
		try {
		    nave1 = ImageIO.read(new File("src/Images/Spaceship1.jpg"));
		} catch (IOException e) {
		}
		try {
		    nave2 = ImageIO.read(new File("src/Images/Spaceship2.jpg"));
		} catch (IOException e) {
		}
		try {
		    tiro1 = ImageIO.read(new File("src/Images/tiroazul.jpg"));
		} catch (IOException e) {
		}
		try {
			for(int i=0; i<6;i++)
		    coracao[i] = ImageIO.read(new File("src/Images/coracao.jpg"));
		} catch (IOException e) {
		}
		
	}
	
	
	public jogo(String name, int controlevindo){
		init();
		setSize(new Dimension(1000, 1000));
		frame = new JFrame("Batalha no Espaço - " + name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		addKeyListener(new Teclado());
		controlevindodecima = (controlevindo != 0);
	  }
	
	
	public String sendnudes() {
		if(controlevindodecima) {
			nudes = ((disparo2 ? 1 : 0)+ " " + controle2);
			if(Posy2<100) {
				nudes += (" 0" + Posy2);
			}else {
				nudes += (" " + Posy2);
			}
			nudes += (" " + (comeca1 ? 1 : 0));
		}else {
			nudes = ((disparo1 ? 1 : 0)+ " " + controle1);
			if(Posy<100) {
				nudes += (" 0" + Posy);
			}else {
				nudes += (" " + Posy);
			}
			nudes += (" " + (comeca ? 1 : 0));
		}
		nudes +=(" " + "sendnudes");
		
		return nudes;
	}
	
	
	public void receivenudes(String chegouosnudes) {
		this.chegouosnudes = chegouosnudes;
		String[] parts = chegouosnudes.split(" ");
		System.out.println("jogo atualizando " + parts[0] +  " " + parts[1] + " " + parts[2]+ " " + parts[3]);
		if(controlevindodecima) {
			disparo1 = "1".equals(parts[0]);
			Posy = Integer.parseInt(parts[2]);
			controle1 = Integer.parseInt(parts[1]);
			comeca = "1".equals(parts[3]);
		}else{
		disparo2 = "1".equals(parts[0]);
		Posy2 = Integer.parseInt(parts[2]);
		controle2 = Integer.parseInt(parts[1]);
		comeca1 = "1".equals(parts[3]);
		}
	}
	
	
	  public synchronized void start() {
	    new Thread(this).start();
	    running = true;
	  }
	  
	
	  public synchronized void stop() {
	    running = false;
	  }
	  
	  
	  @Override
	  public void run() {
	    long lastTime = System.nanoTime();
	    double nsPerTick = 10000000000D/60D;
	    int frames = 0;
	    int ticks = 0;
	    long lasTimer = System.currentTimeMillis();
	    double delta = 0;
	    while(running) {
	      long now = System.nanoTime();
	      delta += (now = lasTimer)/nsPerTick;
	      lastTime = now;
	      boolean shouldRender = false;
	      while (delta >= 1) {
	        ticks++;
	        tick();
	        delta-=1;
	        shouldRender = true;
	      }
	      try {
	        Thread.sleep(2);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	      if (shouldRender) {
	        frames++;
	        render();
	      }    
	      if (System.currentTimeMillis() - lasTimer >= 1000) {
	        lasTimer += 1000;
	        frames = 0;
	        ticks = 0;
	      }
	    }
	    
	  }
	
	  
	  public void tick() {
		  
	    tickCount++;
	    
	  }
	  
	  
	  
	  public void render() {
	    BufferStrategy bs = getBufferStrategy();
	    if (bs == null) {
	      createBufferStrategy(3);
	      return;
	    }
	    Graphics g = bs.getDrawGraphics();
	    if(coracao[0] == null) {
	    	fecha = true;
	    	g.drawImage(player2win, 0, 0, this);
	    }
	    else if(coracao[5] == null) {
	    	fecha= true;
	    	g.drawImage(player1win, 0, 0, this);
	    }
	    else if(comeca || comeca1) {
	    	g.drawImage(inicio, 0, 0, this);
	    }else {
	    	g.drawImage(background, 0, 0, this);
		    g.drawImage(nave1,0 , Posy, this);
		    g.drawImage(nave2,960 , Posy2, this);
		    g.drawImage(coracao[0], 0, 0, this);
		    g.drawImage(coracao[1], 30, 0, this);
		    g.drawImage(coracao[2], 60, 0, this);
		    g.drawImage(coracao[3], 915, 0, this);
		    g.drawImage(coracao[4], 945, 0, this);
		    g.drawImage(coracao[5], 975, 0, this);
	    }
	    c2.alteraposy(Posy);
	    c.alteraposy(Posy2);
	    c.tick();     
	    c.render(g);
	    c2.tick(); 
	    c2.render(g);
	    g.dispose();
	    bs.show();
	    update();
	  }
	  
	  
	  
	  public void update() {
		  if(!comeca && !comeca1) {
			  if(!controlevindodecima) {
				  if(Posy < 951 && Posy > 29)
					  Posy += speed;
					  else {
						  if(Posy>950)
						  Posy-= 1;
						  else {
							Posy+=1;
						  }
					  }
			  }else {
				  if(Posy2 < 951 && Posy2 > 29)
					  Posy2 += speed;
					  else {
						  if(Posy2>950)
						  Posy2-= 1;
						  else {
							Posy2+=1;
						  }
					  }
					  
			  }
			  if(disparo1) { 
				  if(controle1>=60 || controle1<=15) {
					  c.addBullet(new bullet(40,Posy+20,tiro1));
					  controle1=16;					
				  }
				  else {
					  controle1++;
				  }					  
			  } 
			  if(disparo2) { 
				  if(controle2>=60 || controle2<=15) {
					  c2.addBullet(new bullet2(950,Posy2+20,tiro2));
					  controle2=16;
				  }
				  else {
					  controle2++;
				  }	
			  }
	    }
  }

	
	  
	  
	  
	  class Teclado extends KeyAdapter{
	
		  
	  
	  public void keyReleased(KeyEvent e) {
	
		  int key = e.getKeyCode();
		          if (key == 38) {
		              speed = 0;
		          }
		          if (key == 40) {
		              speed = 0;
		          }
		          if(key == 32) {
		        	  if(!controlevindodecima) {
		        		  System.out.println("teste tiro1");
		        		  disparo1=false;
		        		  controle1=60;
		        	  }
		        	  
		        	  else {
		        		  System.out.println("teste tiro2");
		        		  disparo2=false;
			        	  controle2=60;
		        	  }
		        	  
		          }
		  }

	  
	  
	
	    public void keyPressed(KeyEvent e){
	      switch(e.getKeyCode()){
	        case 38:
	          speed = -3;
	          break;
	        case 40:
	        	speed=  3;
	          break;
	        case 32:
	        	if(!controlevindodecima)
	        	disparo1 = true;
	        	else
	        	disparo2 = true;
	        	break;
	        case 10:
	        	if(!controlevindodecima)
	        	comeca =false;
	        	else
	        		comeca1=false;
	        	break;     	
	      }
	    }
	  }
	  
	
	
	public static void main(String[] args) {
	    
	}
	
	
}
