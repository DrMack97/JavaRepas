import java.util.Scanner;

public class eje4 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("dame la frase va ");

        String palabra = sc.nextLine();
        String newe = "";


        for (int i = 0; i < palabra.length(); i++) {

            if (palabra.charAt(i) == 'a') {
                   newe += "i";
            }else {
                newe = newe + palabra.charAt(i);
            }
        }

        System.out.println( newe );

    }
}
