import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

public class Tetris extends JPanel {

	private static final long serialVersionUID = -8715353373678321308L;

	private final Point[][][] Tetraminos = {
			// formato I
			{ { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) } },

			// formato J
			{ { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) } },

			// formato L
			{ { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) } },

			// formato O
			{ { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) } },

			// formato S
			{ { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
					{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) } },

			// formato T
			{ { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) } },

			// formato Z
			{ { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
					{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) } } };

	private final Color[] coresTetraminos = { Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green,
			Color.pink, Color.red };

	private Point origemPedaco;
	private Point origemPedacotabuleiro2;
	private int pedacoAtual;
	private int pedacoAtualTabuleiro2;
	private int rotacao;
	private int rotacaoTabuleiro2;
	private ArrayList<Integer> proximosPedacos = new ArrayList<Integer>();

	public long score;
	public long scoreTabuleiro2;
	public Color[][] well;

	public void inicia() {
		well = new Color[25][24];
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 23; j++) {
				if (i == 0 || i == 11 || i == 12 || i == 23 || i == 24|| j == 22) {
					well[i][j] = Color.GRAY;
				} else {
					well[i][j] = Color.BLACK;
				}
			}
		}

		novoPedaco();
		novoPedacoTabuleiro2();
	}

	public void novoPedaco() {
		origemPedaco = new Point(5, 2);
		rotacao = 0;
		if (proximosPedacos.isEmpty()) {
			Collections.addAll(proximosPedacos, 0, 1, 2, 3, 4, 5, 6);
			Collections.shuffle(proximosPedacos);
		}
		pedacoAtual = proximosPedacos.get(0);
		proximosPedacos.remove(0);
	}

	public void novoPedacoTabuleiro2() {
		origemPedacotabuleiro2 = new Point(17, 2);
		rotacaoTabuleiro2 = 0;
		if (proximosPedacos.isEmpty()) {
			Collections.addAll(proximosPedacos, 0, 1, 2, 3, 4, 5, 6);
			Collections.shuffle(proximosPedacos);
		}
		pedacoAtualTabuleiro2 = proximosPedacos.get(0);
		proximosPedacos.remove(0);
	}

	private boolean colide(int x, int y, int rotacao) {
		for (Point p : Tetraminos[pedacoAtual][rotacao]) {
			if (well[p.x + x][p.y + y] != Color.BLACK) {
				return true;
			}
		}
		return false;
	}

	private boolean colideTabuleiro2(int x, int y, int rotacaoTabuleiro2) {
		for (Point p : Tetraminos[pedacoAtualTabuleiro2][rotacaoTabuleiro2]) {
			if (well[p.x + x][p.y + y] != Color.BLACK) {
				return true;
			}
		}
		return false;
	}

	public void rotaciona(int i) {
		int novaRotacao = (rotacao + i) % 4;
		if (novaRotacao < 0) {
			novaRotacao = 3;
		}
		if (!colide(origemPedaco.x, origemPedaco.y, novaRotacao)) {
			rotacao = novaRotacao;
		}
		repaint();
	}

	public void rotacionatabuleiro2(int i) {
		int novaRotacaoTabuleiro2 = (rotacaoTabuleiro2 + i) % 4;
		if (novaRotacaoTabuleiro2 < 0) {
			novaRotacaoTabuleiro2 = 3;
		}
		if (!colideTabuleiro2(origemPedacotabuleiro2.x, origemPedacotabuleiro2.y, novaRotacaoTabuleiro2)) {
			rotacaoTabuleiro2 = novaRotacaoTabuleiro2;
		}
		repaint();
	}

	public void move(int i) {
		if (!colide(origemPedaco.x + i, origemPedaco.y, rotacao)) {
			origemPedaco.x += i;
		}
		repaint();
	}

	public void movetabuleiro2(int i) {
		if (!colideTabuleiro2(origemPedacotabuleiro2.x + i, origemPedacotabuleiro2.y, rotacaoTabuleiro2)) {
			origemPedacotabuleiro2.x += i;
		}
		repaint();
	}

	public void cai() {
		if (!colide(origemPedaco.x, origemPedaco.y + 1, rotacao)) {
			origemPedaco.y += 1;
		} else {
			fronteiraMuro();
		}
		repaint();
	}

	public void caiTabuleiro2() {
		if (!colideTabuleiro2(origemPedacotabuleiro2.x, origemPedacotabuleiro2.y + 1, rotacaoTabuleiro2)) {
			origemPedacotabuleiro2.y += 1;
		} else {
			fronteiraMuroTabuleiro2();
		}
		repaint();
	}

	public void fronteiraMuro() {
		for (Point p : Tetraminos[pedacoAtual][rotacao]) {
			well[origemPedaco.x + p.x][origemPedaco.y + p.y] = coresTetraminos[pedacoAtual];
		}
		limpaLinhas();
		novoPedaco();
	}

	public void fronteiraMuroTabuleiro2() {
		for (Point p : Tetraminos[pedacoAtualTabuleiro2][rotacaoTabuleiro2]) {
			well[origemPedacotabuleiro2.x + p.x][origemPedacotabuleiro2.y
					+ p.y] = coresTetraminos[pedacoAtualTabuleiro2];
		}
		limpaLinhasTabuleiro2();
		novoPedacoTabuleiro2();
	}

	public void deletaLinha(int row) {
		for (int j = row - 1; j > 0; j--) {
			for (int i = 1; i < 11; i++) {
				well[i][j + 1] = well[i][j];
			}
		}
	}
	public void deletaLinhaTabuleiro2(int row) {
		for (int j = row - 1; j > 0; j--) {
			for (int i = 13; i < 23; i++) {
				well[i][j + 1] = well[i][j];
			}
		}
	}

	public void limpaLinhas() {
		boolean gap;
		int numClears = 0;

		for (int j = 21; j > 0; j--) {
			gap = false;
			for (int i = 1; i < 11; i++) {
				if (well[i][j] == Color.BLACK) {
					gap = true;
					break;
				}
			}
			if (!gap) {
				deletaLinha(j);
				j += 1;
				numClears += 1;
			}
		}

		switch (numClears) {
			case 1:
				score += 100;
				break;
			case 2:
				score += 300;
				break;
			case 3:
				score += 500;
				break;
			case 4:
				score += 800;
				break;
		}
	}

	public void limpaLinhasTabuleiro2() {
		boolean gapTabuleiro2;
		int numClearsTab2 = 0;

		for (int j = 21; j > 0; j--) {
			gapTabuleiro2 = false;
			for (int i = 13; i < 23; i++) {
				if (well[i][j] == Color.BLACK) {
					gapTabuleiro2 = true;
					break;
				}
			}
			if (!gapTabuleiro2) {
				deletaLinhaTabuleiro2(j);
				j += 1;
				numClearsTab2 += 1;
			}
		}

		switch (numClearsTab2) {
			case 1:
				scoreTabuleiro2 += 100;
				break;
			case 2:
				scoreTabuleiro2 += 300;
				break;
			case 3:
				scoreTabuleiro2 += 500;
				break;
			case 4:
				scoreTabuleiro2 += 800;
				break;
		}
	}

	public void desenhaPedaco(Graphics g) {
		g.setColor(coresTetraminos[pedacoAtual]);
		for (Point p : Tetraminos[pedacoAtual][rotacao]) {
			g.fillRect((p.x + origemPedaco.x) * 26, (p.y + origemPedaco.y) * 26, 25, 25);
		}
	}

	public void desenhaPedacoTabuleiro2(Graphics g) {
		g.setColor(coresTetraminos[pedacoAtualTabuleiro2]);
		for (Point p : Tetraminos[pedacoAtualTabuleiro2][rotacaoTabuleiro2]) {
			g.fillRect((p.x + origemPedacotabuleiro2.x) * 26, (p.y + origemPedacotabuleiro2.y) * 26, 25, 25);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.fillRect(0, 0, 26 * 25, 26 * 23);
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 23; j++) {
				g.setColor(well[i][j]);
				g.fillRect(26 * i, 26 * j, 25, 25);

			}
		}

		g.drawString("" + score, 19 * 12, 25);
		g.drawString("" + scoreTabuleiro2, 19 * 30, 25);

		desenhaPedaco(g);
		desenhaPedacoTabuleiro2(g);
	}

}