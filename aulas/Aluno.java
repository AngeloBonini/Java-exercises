public class Aluno extends Pessoa {

    private String registroAcademico;

    static String retornaNome(){
        return nome;
    }
    static Integer retornaTelefone(){
        return telefone;
    }
    static String retornaEndereco(){
        return endereco;
    }
    static String retornaRA(){
        return registroAcademico;
    }

    static String editaNome(String novoNome){
        nome = novoNome;
        return novoNome;
    }
    static String editaTelefone(String novoTelefone){
        telefone = novoTelefone;
        return novoTelefone;
    }
    static String editaEndereco(String novoEndereco){
        endereco = novoEndereco;
        return novoEndereco;
    }
    static String editaRA(String novoRA){
        registroAcademico = novoRA;
        return novoRA;
    }

    static void imprimeValores(){
        System.out.println(nome, telefone, endereco)
    }


    public static void main(String[] args) {
        
        new Aluno()
    }
}
