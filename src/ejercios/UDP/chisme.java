package ejercios.UDP;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class chisme {

    private static class Connection {
        private final Socket socket;
        private final DataInputStream entrada;
        private final DataOutputStream salida;

        Connection(Socket socket) throws IOException {
            this.socket = socket;
            this.entrada = new DataInputStream(socket.getInputStream());
            this.salida = new DataOutputStream(socket.getOutputStream());
        }

        String leer() throws IOException {
            return entrada.readUTF();
        }

        void enviar(String mensaje) throws IOException {
            salida.writeUTF(mensaje);
            salida.flush();
        }

        void close() {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("No se pudo cerrar la conexión: " + e.getMessage());
            }
        }
    }

    private static final int[] PORTS = { 2002, 2003, 2004, 2005, 2006 }; // port list
    private static final int TIMEOUT_MS = 5000; // ¿cuánto esperas una respuesta?
    private static final Random RND = new Random();

    public static void main(String[] args) {
        ServerSocket[] servers = new ServerSocket[PORTS.length];

        try {
            // TAREA 1: se hace UNA vez, fuera del bucle de partidas
            for (int i = 0; i < PORTS.length; i++) {
                servers[i] = new ServerSocket(PORTS[i]);
            }

            // TAREA 6: una partida tras otra
            while (true) {
                jugarPartida(servers);
            }
        } catch (IOException e) {
            System.out.println("No se pudo abrir un puerto: " + e.getMessage());
        } finally {
            // Un array de recursos NO cabe en try-with-resources: ¿por qué?
            // respuesta: los array no tienen autoCloseable
            // Cierra aquí cada ServerSocket que no sea null.

            for (int i = 0; i < servers.length; i++) {
                if (servers[i] != null) {
                    try {
                        servers[i].close();
                    } catch (IOException e) {
                        System.out.println("No se puedo cerrar el servidor adecuadamente " + servers[i]);
                    }

                }

            }

        }
    }

    private static void jugarPartida(ServerSocket[] servers) {
        // Estructura que se "reinicia" en cada partida
        List<Connection> todas = new ArrayList<>();

        try {
            // TAREA 2: aceptar en orden. accept() devuelve ______
            for (ServerSocket s : servers) {
                Socket socket = s.accept();
                socket.setSoTimeout(TIMEOUT_MS); // ¿antes o después de leer?
                todas.add(new Connection(socket));
            }

            // TAREA 3: el primer mensaje llega SIEMPRE por el puerto 2002.
            // ¿Qué posición de la lista es esa?
            Connection origen = todas.get(0);
            String original = origen.leer();

            // Pendientes: quién aún no ha recibido el mensaje.
            // DECISIÓN 1: ¿incluye al origen? Justifícalo en un comentario.
            List<Connection> pendientes = new ArrayList<>(todas);
            pendientes.remove(origen);

            // TAREA 4: el mensaje que viaja
            String mensaje = original;
            while (!pendientes.isEmpty()) {
                mensaje = modificar(mensaje); // el servidor lo cambia
                int posicion = RND.nextInt(PORTS.length + 1 ); // ¿límite exclusivo o inclusivo?
                Connection destino = pendientes.remove(posicion); // sacar de pendientes y devolverlo
                destino.enviar(mensaje);
                mensaje = destino.leer(); // respuesta (puede venir cambiada)
            }

            // TAREA 5a: imprimir
            System.out.println("Original: " + original);
            System.out.println("Final:    " + mensaje);

        } catch (SocketTimeoutException e) { // hija de IOException: va antes
            System.out.println("Un cliente no respondió a tiempo; se aborta la partida");
        } catch (IOException e) {
            System.out.println("Error en la partida: " + e.getMessage());
        } finally {
            // TAREA 5b: se ejecuta SIEMPRE, aunque la partida falle
            for (Connection c : todas) {
                c.close();
            }
            todas.clear(); // reiniciar la estructura
        }
    }

    // La "pequeña modificación": cambiar todas las apariciones de x por y
    private static String modificar(String texto) {
        char x = (char) RND.nextInt(97, 123); // pista: 'a'=97, 'z'=122
        char y = (char) RND.nextInt(97, 123);
        return texto.replace(x, y); // método de String, ¿cuál?
    }
}
