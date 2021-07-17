
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.text.html.StyleSheet;

public class bullet2 {
	private double x;
	
	private double y;
	
	public BufferedImage image;
	
	
	
	public bullet2(double x, double y, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.image = image;
	}
	
	public void tick() {
		x-=6;
	}
	public void render (Graphics g) {
		g.drawImage(image, (int) x, (int) y, null);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	
	
}
