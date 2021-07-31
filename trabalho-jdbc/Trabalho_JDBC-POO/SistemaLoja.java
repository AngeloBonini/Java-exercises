//java -classpath .;hsql.jar SistemaLoja

public class SistemaLoja {
    Pedidos tpedidos;
    Inventario tinventario;
    Assinantes tassinantes;

    SistemaLoja() {
        tpedidos = new Pedidos();
        tinventario = new Inventario();
        tassinantes = new Assinantes();
    }

    public static void main(String[] args) {
        SistemaLoja p = new SistemaLoja();
    }
}