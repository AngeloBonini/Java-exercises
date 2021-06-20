import java.awt.Graphics;

public class Ship {

    constructor(x) {
        this.x = x;
        this.score = 0;
        this.respawn();
        this.raio = 10;

    }

    respawn(){
        this.y = height - 20;
        this.acima = false;
        this.abaixo = false;
    }

    atualiza(){
        if(this.acima && this.y > 0){
            this.sobe();
        } else if(this.abaixo && this.y < height -20 ){
            this.desce();
        }
        if(this.jogadorFezPonto()){
            this.score++;
            this.respawn();
        }
    }

    public void display(Graphics page) {
        page.setColor(Color.white);
        page.fillOval(this.x, this.y, this.raio*2,this.raio*2 );
    }

    sobe(){
        this.y++;
    }

    desce(){
        this.y--;
    }

    jogadorFezPonto(){
        if(this.y <=0){
            return true;
        }
        return false;
    }    

}
