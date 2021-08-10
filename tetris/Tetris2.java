import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

public class Tetris2 extends JPanel {

	private static final long serialVersionUID = -8715353373678321308L;

	private final Point[][][] Tetraminos = {
			// formato I
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) }
			},
			
			// formato J
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }
			},
			
			// formato L
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }
			},
			
			// formato O
			{
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
			},
			
			// formato S
			{
				{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
				{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
			},
			
			// formato T
			{
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) }
			},
			
			// formato Z
			{
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
			}
	};
	
	private final Color[] coresTetraminos = {
		Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red
	};
	
	private Point origemPedaco;
	private int pedacoAtual;
	private int rotacao;
	private ArrayList<Integer> proximosPedacos = new ArrayList<Integer>();

	public long score;
	public Color[][] well;



	public void inicia() {
		well = new Color[362][24];
		for (int i = 350; i < 362; i++) {
			for (int j = 0; j < 23; j++) {
				if (i == 350 || i == 361 || j == 22) {
					well[i][j] = Color.GRAY;
					System.out.println(well[i][j]);
				} else {
					well[i][j] = Color.BLACK;
					System.out.println(well[i][j]);
				}
			}
		}
	
		novoPedaco();
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
	

	private boolean colide(int x, int y, int rotacao) {
		for (Point p : Tetraminos[pedacoAtual][rotacao]) {
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
	
	public void move(int i) {
		if (!colide(origemPedaco.x + i, origemPedaco.y, rotacao)) {
			origemPedaco.x += i;	
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
	
	public void fronteiraMuro() {
		for (Point p : Tetraminos[pedacoAtual][rotacao]) {
			well[origemPedaco.x + p.x][origemPedaco.y + p.y] = coresTetraminos[pedacoAtual];
		}
		limpaLinhas();
		novoPedaco();
	}
	
	public void deletaLinha(int row) {
		for (int j = row-1; j > 0; j--) {
			for (int i = 1; i < 11; i++) {
				well[i][j+1] = well[i][j];
			}
		}
	}
	
	public void limpaLinhas() {
		boolean gap;
		int numClears = 0;
		
		for (int j = 21; j > 0; j--) {
			gap = false;
			for (int i = 351; i < 361; i++) {
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
	
	public void desenhaPedaco(Graphics g) {		
		g.setColor(coresTetraminos[pedacoAtual]);
		for (Point p : Tetraminos[pedacoAtual][rotacao]) {
			g.fillRect((p.x + origemPedaco.x) * 26, 
					   (p.y + origemPedaco.y) * 26, 
					   25, 25);
		}
	}
	
	@Override 
	public void paintComponent(  Graphics g)
	{
		g.fillRect(350, 0, 26*12, 26*23);
		for (int i = 350; i < 362; i++) {
			for (int j = 0; j < 23; j++) {
				g.setColor(well[i][j]);
				g.fillRect(i, 25*j, 302, 26);
				
			}
		}


		
		g.setColor(Color.WHITE);
		g.drawString("" + score, 19*12, 25);
		
		desenhaPedaco(g);
	}

}