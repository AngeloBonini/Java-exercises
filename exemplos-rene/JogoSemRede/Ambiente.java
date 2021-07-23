import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Ambiente extends JPanel {
  static final int LARG_JOGO = 800;
  static final int ALTU_JOGO = 600;
  static final int FUNDO = 0;
  static final int ARBUSTO = 1;
  static final int GRAMA = 2;

  JogadorDesenho jogador;
  JogadorDesenho adversario;
  Image[] imagens = new Image[3];

  Ambiente(JogoSemRede jogo) {
    setPreferredSize(new Dimension(LARG_JOGO, ALTU_JOGO));
    carregaImagens();
    jogador = new JogadorDesenho(50, ALTU_JOGO - 15, jogo);
    adversario = new JogadorDesenho(LARG_JOGO - 50, ALTU_JOGO - 15, jogo);
    adversario.inverte(true);
  }

  void carregaImagens() {
    try {
      imagens[FUNDO] = ImageIO.read(new File("fundo.jpeg"));
      imagens[ARBUSTO] = ImageIO.read(new File("arbusto.png"));
      imagens[GRAMA] = ImageIO.read(new File("grama.gif"));
    } catch (IOException e) {
      JOptionPane.showMessageDialog(this, "A imagem não pode ser carregada!\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.clipRect(5, 5, getWidth() - 10, getHeight() - 10);

    g.drawImage(imagens[FUNDO], 0, 0, LARG_JOGO, ALTU_JOGO, this);

    jogador.desenha(g);
    adversario.desenha(g);

    g.drawImage(imagens[ARBUSTO], 300, ALTU_JOGO - imagens[ARBUSTO].getHeight(this) - 10, this);
    g.drawImage(imagens[GRAMA], 0, 0, LARG_JOGO, ALTU_JOGO, this);

    desenhaPlacar(g);
    
    Toolkit.getDefaultToolkit().sync();
  }

  void desenhaPlacar(Graphics g) {
    Font f = new Font("Arial", Font.BOLD, 50);
    g.setFont(f);
    String s = jogador.pontos + " x " + adversario.pontos;
    FontMetrics fm = g.getFontMetrics();
    int x = (LARG_JOGO - fm.stringWidth(s)) / 2;
    g.drawString(s, x - 1, fm.getHeight() - 1);
    g.setColor(Color.RED);
    g.drawString(s, x + 1, fm.getHeight() + 1);
  }

  boolean trataColisaoLimites(Jogador j) {
    if (j.x + j.rectColisao.width + 5 > LARG_JOGO) {
      j.posicao(LARG_JOGO - j.rectColisao.width - 5);
      return true;
    } else if (j.x < 5) {
      j.posicao(5);
      return true;
    }
    return false;
  }

  void trataColisao() {
    int dx = 10;
    int dxJogador = dx;
    int dxAdversario = dx;
    if (trataColisaoLimites(jogador)) {
      dxJogador = 0;
      dxAdversario = 2 * dx;
    }
    if (trataColisaoLimites(adversario)) {
      dxJogador = 2 * dx;
      dxAdversario = 0;
    }
    if (jogador.verificaColisao(adversario)) {
      if (jogador.estado == Jogador.SOCO2) {
        jogador.pontos++;
      }
      if (jogador.x < adversario.x) {
        jogador.posicao(jogador.x - dxJogador);
        adversario.posicao(adversario.x + dxAdversario);
      } else {
        jogador.posicao(jogador.x + dxJogador);
        adversario.posicao(adversario.x - dxAdversario);
      }
      repaint();
    }
  }

  void verificaAnda() {
    if (jogador.estado == Jogador.ANDA0) {
      jogador.estado(Jogador.ANDA1);
      jogador.posicao(jogador.x + 10);
      repaint();
    } else if (jogador.estado == Jogador.ANDA1) {
      jogador.estado(Jogador.ANDA0);
      jogador.posicao(jogador.x + 10);
      repaint();
    }
  }

  void verificaSoco() {
    if (jogador.estado == Jogador.SOCO0) {
      jogador.estado(Jogador.SOCO1);
      repaint();
    } else if (jogador.estado == Jogador.SOCO1) {
      jogador.estado(Jogador.SOCO2);
      repaint();
    } else if (jogador.estado == Jogador.SOCO2) {
      jogador.estado(Jogador.PARADO);
      repaint();
    }
  }
}