import java.util.Scanner;

public class eje8 {


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

            System.out.println("que movimiento quieres hacer? ");
            String move = sc.nextLine();

            switch (move) {
                case "A":
                    
                    columna--;
                    break;

                case "S":
                    fila++;
                    
                    break;
                case "D":
                    columna++;
                    break;
                case "W":
                    fila--;
                    
                    break;
                case "F":
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
                }
                System.out.print("[ ]");
            }
            System.out.println(" ");
        }
    }
}
