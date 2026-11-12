import java.util.Random;

public class MatricesPracticas {
    public static void main(String[] args) {

        char[][] partida = nuevoTablero();

        mostrar(partida);

        mostrar(fichaMaquina(partida,'x'));
        fichaMaquina(partida, 'x');
        fichaMaquina(partida, 'x');

        mostrar(partida);
        System.out.println(lleno(partida));
        System.out.println(gano(partida, 'x'));
    }



    static public boolean colocar(char[][] tablero, int fila, int col, char ficha) {

        if (fila < 0 || fila >= 3 || col < 0 || col >= 3)
            return false;
        if (tablero[fila][col] != ' ')
            return false;

        tablero[fila][col] = ficha;

        return true;
    }

    static public char[][] fichaMaquina(char[][] tablero, char ficha) {
        Random rnd = new Random();
        int fila = 0;
        int col = 0;
        do {
            fila = rnd.nextInt(3);
            col = rnd.nextInt(3);

        } while (tablero[fila][col] != ' ');
        colocar(tablero, fila, col, ficha);
        tablero[fila][col] = ficha;

        System.out.println("La maquina marca en:" + fila + " - " + col);
        

        return tablero;
    }

    /**
     * 
     * 
     * // 4) MODIFICAR con validación (lo que hará el servidor)
     * ---------------------
     * static void ejemplo4Modificar() {
     * System.out.println("--- 4) Modificar con validación ---");
     * char[][] tablero = nuevoTablero();
     * 
     * System.out.println(colocar(tablero, 1, 1, 'X')); // true
     * System.out.println(colocar(tablero, 1, 1, 'O')); // false (ocupada)
     * System.out.println(colocar(tablero, 3, 0, 'O')); // false (fuera de rango)
     * 
     * // Movimiento aleatorio de la "máquina": busca una casilla libre
     * Random azar = new Random();
     * int fila, col;
     * do {
     * fila = azar.nextInt(3); // 0, 1 o 2
     * col = azar.nextInt(3);
     * } while (tablero[fila][col] != VACIO);
     * tablero[fila][col] = 'O';
     * System.out.println("Máquina juega en (" + fila + "," + col + ")");
     * mostrar(tablero);
     * // Cuidado: este do-while se cuelga si el tablero está lleno -> comprueba
     * antes.
     * }
     * 
     * /** Devuelve true si la ficha se colocó; false si la posición no es válida o
     * está ocupada.
     * static boolean colocar(char[][] tablero, int fila, int col, char ficha) {
     * if (fila < 0 || fila >= 3 || col < 0 || col >= 3) return false;
     * if (tablero[fila][col] != VACIO) return false;
     * tablero[fila][col] = ficha;
     * return true;
     * }
     * 
     * // 5) COMPROBAR: tablero lleno y ganador
     * -------------------------------------
     * static void ejemplo5Comprobar() {
     * System.out.println("--- 5) Comprobar ---");
     * char[][] tablero = nuevoTablero();
     * colocar(tablero, 0, 2, 'X');
     * colocar(tablero, 1, 1, 'X');
     * colocar(tablero, 2, 0, 'X'); // diagonal secundaria
     * mostrar(tablero);
     * System.out.println("Lleno? " + estaLleno(tablero)); // false
     * System.out.println("Gana X? " + haGanado(tablero, 'X')); // true
     * System.out.println("Gana O? " + haGanado(tablero, 'O')); // false
     * }
     * 
     * static boolean estaLleno(char[][] t) {
     * for (char[] fila : t)
     * for (char c : fila)
     * if (c == VACIO) return false;
     * return true;
     * }
     * 
     * static boolean haGanado(char[][] t, char f) {
     * for (int i = 0; i < 3; i++) {
     * if (t[i][0] == f && t[i][1] == f && t[i][2] == f) return true; // fila i
     * if (t[0][i] == f && t[1][i] == f && t[2][i] == f) return true; // columna i
     * }
     * if (t[0][0] == f && t[1][1] == f && t[2][2] == f) return true; // diagonal
     * return t[0][2] == f && t[1][1] == f && t[2][0] == f; // antidiagonal
     * }
     * 
     */

    static public boolean gano(char[][] t, char ficha){
        for (int i = 0; i < t.length; i++) {
            if (t[i][0] == ficha && t[i][1] == ficha && t[i][2] == ficha) return true;
            if (t[0][i] == ficha && t[1][i] == ficha && t[2][i] == ficha) return  true;
            
        }

        //diagonales: 
        if (t[0][0] == ficha && t[1][1] == ficha && t[2][2] == ficha) return true;

        if (t[0][2] == ficha && t[1][1] == ficha && t[2][0] == ficha) return  true;

        return false; 
    }

    static public boolean lleno(char[][] t){

        for (char[] fila : t) {
            for (char col : fila) {
                if (col == ' ') return false;
            }
        }
        return true;
    }


    // mostrar:

    static public void mostrar(char[][] table) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(" " + table[i][j] + " ");
                if (j < table[i].length - 1)
                    System.out.print("|");
            }
            System.out.println();
            if (i < table.length - 1)
                System.out.println("---+---+---");
        }
    }

    static public char[][] nuevoTablero() {
        char[][] table = new char[3][3];

        for (char[] fila : table) {
            java.util.Arrays.fill(fila, ' ');
        }

        return table;
    }

    static public void declararEjemplo() {
        char[][] tablero = new char[3][3];

        System.out.println("Medidas: " + tablero.length + " " + tablero[0].length);
        System.out.println("que pinta? =>" + tablero[1][1]);

        System.out.println("esta vacio? " + (tablero[0][0] == ' '));

        int[][] lineas = // otra matriz 3x3
                {
                        { 1, 2, 3 },
                        { 4, 5, 6 },
                        { 7, 8, 9 }
                };

        System.out.println(lineas[2][2]);
    }

    static public void tablero() {
        char[][] tablero = new char[3][3];

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = ' ';
            }
        }
        /*
         * i = Fila
         * j = col
         */
    }
}
