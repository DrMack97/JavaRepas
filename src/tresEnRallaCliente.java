import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class tresEnRallaCliente {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 27000;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try (Socket socket = new Socket(HOST, PORT)) {

            socket.setSoTimeout(5000);
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            out.writeUTF("START");
            out.flush();

            boolean partidaEnCurso = true;

            do {
                String tablero = in.readUTF();
                boolean movimientoAceptado = false;

                System.out.println(tablero + "\ncual es tu movimiento? ");

                while (!movimientoAceptado) { // se mantine mientras el jugador haga una jugada equivocada
                    try {
                        int fila;
                        int columna;
                        do {
                            System.out.println("elije fila: (0 - 1 - 2)"); // cordenada x
                            String x = sc.nextLine().trim();
                            fila = Integer.parseInt(x);

                        } while (!valido(fila));

                        // enviar a servidor despues de validar que sea un numero y esta en el rango

                        do {
                            System.out.println("elije columna: (0 - 1 - 2"); // cordenada y
                            String y = sc.nextLine().trim();
                            columna = Integer.parseInt(y);
                        } while (!valido(columna));

                        out.writeInt(fila);
                        out.writeInt(columna);
                        out.flush(); // se envian juntas las cordenadas 

                        movimientoAceptado = in.readBoolean();// Se valida la jugada en el servidor, si recive false se
                                                              // repite

                    } catch (NumberFormatException e) {
                        System.out.println(" no es un numero valido ");
                    }
                    if (!movimientoAceptado) {
                        System.out.println("Movimiento invalido, prueba de nuevo");
                    }
                }
                

                partidaEnCurso = in.readBoolean();
            } while (partidaEnCurso);
            // Ganador:

            String ganador = in.readUTF();

            System.out.println("Ganador: " + ganador);

        } catch (EOFException e) {
            System.out.println("Error de comunicacion");
        } catch (ConnectException e) {
            System.out.println("Servidor caido");
        } catch (SocketTimeoutException e) {
            System.out.println("Servidor no respindio a tiempo");
        } catch (UnknownHostException e) {
            System.out.println("Host no encontrado");
        } catch (IOException e) {
            System.out.println("Error de comunicacion: ");
        }

    }

    static public boolean valido(int digito) {

        if (digito >= 0 && digito <= 2) {
            return true;
        } else {
            return false;
        }
    }

}
