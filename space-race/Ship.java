import java.awt.Graphics;

public class Ship {
    Image ship = new Image[1];
int x;
int y;
int score;
int raio;
boolean acima;
boolean abaixo;

    public Ship() {
        this.x = 120;
        this.score = 0;
        this.respawn();
        this.raio = 10;
        ship[0] = ImageIO.read(new File("pixil-frame-0.png"));
    }

    public void respawn(){
        this.y = 880;
        this.acima = false;
        this.abaixo = false;
    }

    public void  atualiza(){
        if(this.acima && this.y > 0){
            this.sobe();
        } else if(this.abaixo && this.y < 880 ){
            this.desce();
        }
        if(this.jogadorFezPonto()){
            this.score++;
            this.respawn();
        }
    }


    public void sobe(){
        this.y++;
    }

    public void desce(){
        this.y--;
    }

    public boolean jogadorFezPonto(){
        if(this.y <=0){
            return true;
        }
        return false;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img[0],  30, 60, this);
        Toolkit.getDefaultToolkit().sync();
      }    
public static void main(String[] args){
    Ship nave = new Ship();
}
}
