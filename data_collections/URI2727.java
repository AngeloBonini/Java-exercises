
import java.io.IOException;
import java.util.Scanner;

public class URI2727 {
    public static void main(String[] args) throws IOException {
    	Scanner leitor = new Scanner(System.in);
    	while (leitor.hasNext()) {
    		int N = leitor.nextInt();
    		for (int i = 0; i < N; i++) {
    			String[] codigo = readLine(leitor).split(" ");
    			char letra = (char) (96 + (((codigo.length - 1) * 3) + codigo[0].length()));
    			System.out.println(letra);
    		}
    	}
    }
    
    public static String readLine(Scanner leitor) {
    	String line = leitor.nextLine();
    	while (line.isEmpty())
    		line = leitor.nextLine();
    	return line;
    }
}

