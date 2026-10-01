package ejercios.Repas;

import java.util.Scanner;

public class eje5 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("elige el caracter ");
        String caracter = sc.nextLine();

        System.out.println("alto: ");
        int alto = sc.nextInt();
        System.out.println("ancho: ");
        int ancho = sc.nextInt();

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                if((i+j)%2 == 0){
                    System.out.print("[ ]");
                }else {
                    System.out.print("["+caracter+"]");
                }
            }
            System.out.println(" ");
        }

    }
}
