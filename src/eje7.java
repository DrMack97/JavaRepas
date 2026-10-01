import java.util.Scanner;

public class eje7 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        int op = -1;

        do {

            System.out.println("Calculadora de Binario a decimal y viseversa ");
            System.out.println();
            System.out.println(" 1. introducir binario ");
            System.out.println(" 2. introducir Decimal ");
            System.out.println(" 0. Salir ");

            op = sc.nextInt();

            switch (op) {
                case 1:
                    System.out.println("num binario: ");
                    System.out.println("x0");
                    int x0 = sc.nextInt();
                    sc.nextLine();
                    System.out.println("x1");
                    int x1 = sc.nextInt();
                    sc.nextLine();
                    System.out.println("x2");
                    int x2 = sc.nextInt();

                    int decimal = convertidorBinarioADecimal(x0, x1, x2);

                    System.out.println("decimal: " + decimal);
                    break;

                case 2:
                    System.out.println("ingrese decimal: ");
                    int decimal2 = sc.nextInt();

                    String binario = convertDecimalABinario(decimal2);

                    System.out.println("Binario: " + binario);
                    break;

                default:
                    System.out.println("opcion equivocada ");
                    break;
            }

        } while (salir != true);

    }

    static public int convertidorBinarioADecimal(int x2, int x1, int x0) {
        int decimal = 0;

        decimal = x2 * 4 + x1 * 2 + x0 * 1;

        return decimal;
    }

    static public String convertDecimalABinario(int decimal) {
        String binario = "";
        if (decimal < 0) {
            return binario = "no acepto negativos ";
        }

        while (decimal >= 1) {
            int residuo = decimal % 2;
            decimal = decimal / 2;
            

            if (residuo == 0) {
                binario = residuo + binario;
            } else {
                binario = residuo + binario;
            }
        }

        return binario;
    }

}
