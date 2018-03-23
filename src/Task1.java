import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] arg) {
    	
    	
		try {
			List<Expression> axes = Parser.get_ax("axioms.txt", false);
			try {
				Scanner sc = new Scanner(new File("input1.txt"));
				List<Expression> assu = Parser.get_assu(sc);
				try {
					PrintWriter out = new PrintWriter("output1.txt");
					new Solver().annotate(sc,  out, axes, assu);
					
					out.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				sc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
}
