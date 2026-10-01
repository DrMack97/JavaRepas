package ejercios.Repas;

import java.util.Scanner;

public class eje2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double primero, segundo;
        double result;

        System.out.println(" lets take two numbers and divide them ");
        primero = sc.nextInt();
        segundo = sc.nextInt();

        result = primero / segundo;

        System.out.printf("primer num %.0f second num %.0f resultado = %.3f",primero, segundo, result);

    }
}
