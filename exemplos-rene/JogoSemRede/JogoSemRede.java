import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

public class JogoSemRede extends JFrame {
  Rectangle[] rect;
  Ambiente amb = new Ambiente(this);

  JogoSemRede() {
    super("Jogo");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    add(amb);
    pack();
    setResizable(false);
    setVisible(true);

    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
          amb.jogador.estado(Jogador.ANDA0);
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
          amb.jogador.estado(Jogador.PARADO);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
          amb.jogador.estado(Jogador.SOCO0);
        } else if (e.getKeyCode() == KeyEvent.VK_I) {
          amb.jogador.inverte(!amb.jogador.invertido);
        }
        amb.repaint();
      }
    });

    new Timer(100, new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        amb.verificaAnda();
        amb.verificaSoco();
        amb.trataColisao();
      }
    }).start();
  }

  static public void main(String[] args) {
    new JogoSemRede();
  }
}
