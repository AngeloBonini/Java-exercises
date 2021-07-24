import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel{


    
    public static void main(String[] args) {
		JFrame f = new JFrame("Tetris");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(12*26+500, 52*23+50);
        // f.setSize(12*26+10, 26*23+25);
		f.setVisible(true);
		
		final Tetris jogador1 = new Tetris(0, 0);
		jogador1.init();
		f.add(jogador1);

			final Tetris jogador2 = new Tetris(400, 0 );
		jogador2.init();
		f.add(jogador2);
		// Keyboard controls

		f.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					jogador1.rotate(-1);
					jogador2.rotate(-1);
					break;
				case KeyEvent.VK_DOWN:
					jogador1.rotate(+1);
					jogador2.rotate(+1);
					break;
				case KeyEvent.VK_LEFT:
					jogador1.move(-1);
					jogador2.move(-1);
					break;
				case KeyEvent.VK_RIGHT:
					jogador1.move(+1);
					jogador2.move(+1);
					break;
				case KeyEvent.VK_SPACE:
					jogador1.dropDown();
					jogador2.dropDown();
					jogador1.score += 1;
					jogador2.score += 1;
					break;
				} 
			}
			
			public void keyReleased(KeyEvent e) {
			}
		});
		
		// Make the falling piece drop every second
		new Thread() {
			@Override public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
						jogador1.dropDown();
						jogador2.dropDown();
					} catch ( InterruptedException e ) {}
				}
			}
		}.start();
	}
}
