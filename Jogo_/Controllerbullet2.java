
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Controllerbullet2 {
	private LinkedList<bullet2> b = new LinkedList<bullet2>();
	bullet2 TempBullet;
	public int posy;
	BufferedImage image;
	
	public void alteraposy(int posy) {
		this.posy = posy; 
	}
	
	
	public Controllerbullet2(BufferedImage image) {
		this.image = image;
		
		
	}
	
	

	public void tick() {
		for(int i=0;i< b.size(); i++) {
			TempBullet = b.get(i);
			if(TempBullet.getX() < -1) {
				removeBullet(TempBullet);
			}else if(TempBullet.getX() < 40 && (TempBullet.getY() > (posy+20-15) && (TempBullet.getY() < (posy+20+15)  ) )) {
				removeBullet(TempBullet);
				jogo.coracao[jogo.coracrl--] = null;
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
	
	public void addBullet(bullet2 bullet) {
		b.add(bullet);
	}
	
	public void removeBullet(bullet2 tempBullet2) {
		b.remove(tempBullet2);
	}
}
