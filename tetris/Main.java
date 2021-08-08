import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {

	public static void main(String[] args) {
		JFrame f = new JFrame("Tetris");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(12 * 26 + 500, 52 * 23 + 10);

		f.setVisible(true);

		final Tetris jogador1 = new Tetris(0, 0);
		final Tetris2 jogador2 = new Tetris2(350, 0);
		jogador1.inicia();
		jogador2.inicia();
		f.add(jogador1);
		f.add(jogador2);

		f.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						jogador1.rotaciona(-1);
						break;
					case KeyEvent.VK_DOWN:
						jogador1.rotaciona(+1);
						break;
					case KeyEvent.VK_LEFT:
						jogador1.move(-1);
						break;
					case KeyEvent.VK_RIGHT:
						jogador1.move(+1);
						break;
					case KeyEvent.VK_SPACE:
						jogador1.cai();
						jogador1.score += 1;
						break;
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		});

		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
						jogador1.cai();
					} catch (InterruptedException e) {
					}
				}
			}
		}.start();

	}
}
