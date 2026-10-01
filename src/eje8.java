import java.util.Scanner;

public class eje8 {

    //Para resolver: 
    /*
        1- pedir tamaño y posicion al user (Scanner)
        2- permetir moverse por el tablero preguntando su direccion (doWhile/Switch)
        3- metodo para validar que el movimiento este dentro del tablero
        4- metodo para pintar el tablero y al personaje (doble bucle for)
    
     */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int fila = 0;
        int columna = 0;
        boolean exit = false;

        System.out.println("cual es el tamano del tablero?");
        int n = sc.nextInt();

        System.out.println("cual es tu posicion inicial? ");
        fila = sc.nextInt();
        columna = sc.nextInt();
        sc.nextLine();

        System.out.println("tablero:");
        tablero(n,fila,columna);

        do {

            System.out.println("\nque movimiento quieres hacer? ");
            String move = sc.nextLine().toUpperCase();

            switch (move) {
                case "A":
                    if (!validMove(n, fila, columna - 1)) {
                        System.out.println("movimiento invalido");
                    } else {
                        columna--;
                    }
                    break;
                case "S":
                    if (!validMove(n, fila + 1, columna)) {
                        System.out.println("movimiento invalido");
                    } else {
                        fila++;
                    }
                    break;
                case "D":
                    if (!validMove(n, fila, columna + 1)) {
                        System.out.println("movimiento invalido");
                    } else {
                        columna++;
                    }
                    break;
                case "W":
                    if (!validMove(n, fila - 1, columna)) {
                        System.out.println("movimiento invalido");
                    } else {
                        fila--;
                    }
                    break;
                case "F" :
                    System.out.println("adios!!");
                        exit = true;
                    break;
            
                default:
                    System.out.println("tecla equivocada, prueba de nuevo!s");
                    break;
            }

            tablero(n, fila, columna);
            
        } while (exit != true);


    }

    static public boolean validMove(int n, int fila, int columna){
        boolean valido = true;
        if ((fila >= n || columna >= n) || (fila < 0 || columna < 0)) {
            valido = false;
        }

        return valido;
    }

    static public void tablero(int n, int fila, int columna){

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == fila && j == columna) {
                    System.out.print("[P]");
                }else{
                    System.out.print("[ ]");
                }
            }
            System.out.println(" ");
        }
    }
}
