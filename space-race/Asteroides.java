import java.util.Random;

public class Asteroides {
    
    constructor(){ 
        this.raio = 5;
        this.resetAteroides();
    }

    resetAteroides(){
        Random gerador = new Random(height -70);
        this.y = gerador;
    }
}
