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
		f.setSize(12 * 26 + 500, 52 * 15 + 10);

		f.setVisible(true);

		final Tetris jogadores = new Tetris();
		jogadores.inicia();
		f.add(jogadores);

		f.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						jogadores.rotaciona(-1);
						break;
					case KeyEvent.VK_DOWN:
						jogadores.rotaciona(+1);
						break;
					case KeyEvent.VK_LEFT:
						jogadores.move(-1);
						break;
					case KeyEvent.VK_RIGHT:
						jogadores.move(+1);
						break;
					case KeyEvent.VK_SPACE:
						jogadores.cai();
						break;

						case KeyEvent.VK_W:
						jogadores.rotacionatabuleiro2(-1);
						break;
					case KeyEvent.VK_S:
						jogadores.rotacionatabuleiro2(+1);
						break;
					case KeyEvent.VK_A:
						jogadores.movetabuleiro2(-1);
						break;
					case KeyEvent.VK_D:
						jogadores.movetabuleiro2(+1);
						break;
					case KeyEvent.VK_T:
						jogadores.caiTabuleiro2();
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
						jogadores.cai();
						jogadores.caiTabuleiro2();
					} catch (InterruptedException e) {
					}
				}
			}
		}.start();

	

	}
}
