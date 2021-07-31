//java -classpath .;hsql.jar SistemaLoja

public class SistemaLoja {
    Pedidos tpedidos;
    Inventario tinventario;
    Funcionarios tfuncionarios;

    SistemaLoja() {
        tpedidos = new Pedidos();
        tinventario = new Inventario();
        tfuncionarios = new Funcionarios();
    }

    public static void main(String[] args) {
        SistemaLoja p = new SistemaLoja();
    }
}