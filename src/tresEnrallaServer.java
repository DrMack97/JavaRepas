import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Random;

public class tresEnrallaServer {

    private static final int PORT = 27000;
    private static Random rnd = new Random();

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) { // una partida por conexión; al acabar, nueva conexión
                try (Socket con = server.accept()) {
                    con.setSoTimeout(10000);
                    DataInputStream in = new DataInputStream(new BufferedInputStream(con.getInputStream()));
                    DataOutputStream out = new DataOutputStream(new BufferedOutputStream(con.getOutputStream()));

                    String inicio = in.readUTF();
                    if (inicio.equalsIgnoreCase("START")) { // empezar partida
                        jugarPartida(in, out);
                    } else {
                        out.writeUTF("Apagant...");
                        con.close(); // cerrar conexion
                    }

                } catch (SocketTimeoutException e) {
                    System.out.println("el cliente tardó demasiado");
                } catch (EOFException e) {
                    System.out.println("Cliente se a cerro conexion");
                } catch (IOException e) {
                    System.out.println("Fallo conexion del cliente");
                }
            }

        } catch (IOException e) {
            System.out.println("sin conexion con el puerto indicado ");
        }
    }

    static void jugarPartida(DataInputStream in, DataOutputStream out) throws IOException {
        // crear tablero
        char[][] tableroServer = nuevoTablero();

        // decidir quién empieza y con qué ficha (X u O)
        boolean moneda = rnd.nextBoolean();
        char ficharServer = moneda ? 'X' : 'O';
        char fichaCliente = moneda ? 'O' : 'X';

        // si empieza el server:
        if (ficharServer == 'X') {
            movimientoServer(tableroServer, ficharServer);
        }

        boolean partidaEnCurso = true;
        String ganador = "";

        while (partidaEnCurso) {
            // enviar el tablero
            String enviarTablero = comoTexto(tableroServer);
            out.writeUTF(enviarTablero);
            out.flush();

            boolean aceptado = false;
            while (!aceptado) {
                // leer
                int fila = in.readInt();
                int columna = in.readInt();
                // validar
                if (colocar(tableroServer, fila, columna, fichaCliente)) {
                    tableroServer[fila][columna] = fichaCliente;
                    aceptado = true;
                }
                // enviar
                out.writeBoolean(aceptado);
                out.flush();

            }

            // validar ganador

            if (haGanado(tableroServer, ficharServer)) {
                ganador = "SERVER";
            } else if (haGanado(tableroServer, fichaCliente)) {
                ganador = "CLIENTE";
            } else if (estaLleno(tableroServer) ) {
                ganador = "EMPATE";
            }
            // terminar partida 
            if (!ganador.isBlank()) {
                partidaEnCurso = false;
            }

            // si sigue mover maquina y re comprobar tablero
            if (partidaEnCurso) {
                movimientoServer(tableroServer, ficharServer);
                partidaEnCurso = estaLleno(tableroServer);
            }

            out.writeBoolean(partidaEnCurso);
            out.flush();
            // writeBoolean(partidaEnCurso) + flush

        }

        // enviar ganador: 
        out.writeUTF(ganador);
        out.flush();

    }

    static char[][] nuevoTablero() { // inicializado vacio
        char[][] tablero = new char[3][3];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                tablero[i][j] = ' ';
            }
        }
        return tablero;
    }

    static String comoTexto(char[][] t) {
        StringBuilder sb = new StringBuilder();
        for (char[] fila : t) {
            for (char columna : fila) {
                sb.append(columna).append(' '); // añade la casilla
                // seguida de un espacio para separar las columnas
            }
            sb.append('\n'); // salto de línea al terminar cada fila
        }
        return sb.toString();
    }

    static boolean colocar(char[][] tablero, int fila, int col, char ficha) {
        if (fila < 0 || fila >= 3 || col < 0 || col >= 3) // solo 0 - 1 - 2
            return false;
        if (tablero[fila][col] != ' ')// si no esta vacio
            return false;

        tablero[fila][col] = ficha;

        return true;

    }

    static boolean haGanado(char[][] t, char ficha) {

        for (int i = 0; i < t.length; i++) {
            if (t[i][0] == ficha && t[i][1] == ficha && t[i][2] == ficha)
                return true;
            if (t[0][i] == ficha && t[1][i] == ficha && t[2][i] == ficha)
                return true;

        }

        // diagonales:
        if (t[0][0] == ficha && t[1][1] == ficha && t[2][2] == ficha)
            return true;

        if (t[0][2] == ficha && t[1][1] == ficha && t[2][0] == ficha)
            return true;

        return false;
    }

    static boolean estaLleno(char[][] t) {
        for (char[] fila : t) {
            for (char col : fila) {
                if (col == ' ')
                    return false;
            }
        }
        return true;
    }

    static void movimientoServer(char[][] tablero, char ficha) {
        int fila = 0, col = 0;
        do {
            fila = rnd.nextInt(3);
            col = rnd.nextInt(3);

        } while (!(colocar(tablero, fila, col, ficha)));

    }
}
