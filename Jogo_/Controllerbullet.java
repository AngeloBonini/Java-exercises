
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Controllerbullet {
	private LinkedList<bullet> b = new LinkedList<bullet>();
	bullet TempBullet;
	int posy;
	BufferedImage image;
	
	public Controllerbullet(BufferedImage image) {
		this.image = image;
		
		
	}
	

	public void alteraposy(int posy) {
		this.posy = posy; 
	}
	
	

	public void tick() {
		for(int i=0;i< b.size(); i++) {
			TempBullet = b.get(i);
			if(TempBullet.getX() > 981) {
				removeBullet(TempBullet);
			}else if(TempBullet.getX() > 951 && (TempBullet.getY() > (posy+20-15) && (TempBullet.getY() < (posy+20+15)  ) )) {
				removeBullet(TempBullet);
				jogo.coracao[jogo.coracrl2++] = null;
			
			}
			TempBullet.tick();
			
		}
	}
	
	
	public void render (Graphics g) {
		for (int i = 0; i<b.size(); i++) {
			TempBullet = b.get(i);
			TempBullet.render(g);
		}
	}
	
	public void addBullet(bullet block) {
		b.add(block);
	}
	
	public void removeBullet(bullet block) {
		b.remove(block);
	}
}
