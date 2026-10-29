package ejercios.TCP;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

/**
 * Servidor MFTPP.
 * Fase 1 (UDP): recibe el aviso "puerto,nombre,fecha" de un cliente.
 * Fase 2 (TCP): se conecta al puerto indicado y lee datos hasta que el cliente cierra.
 * Al terminar, registra la conexión en MFTPP.log (CSV: nom;port;inicio;fin).
 */
public class MFTPPservidor {

    // ===== CONSTANTES =====
    private static final int UDP_PORT = 2705;
    private static final int BUFFER_SIZE = 1024;
    private static final int TIMEOUT_MS = 5000;
    private static final String ARCHIVO = "MFTPP.log";
    private static final String SEPARADOR = ","; // formato del aviso: puerto,nombre,fecha

    // ===== PROGRAMA PRINCIPAL: solo el "andamio" =====
    public static void main(String[] args) {
        // Se hace UNA vez: abrir el puerto UDP y preparar el buffer
        try (DatagramSocket udpSocket = new DatagramSocket(UDP_PORT)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            System.out.println("Servidor MFTPP escuchando en UDP " + UDP_PORT);

            // Se hace POR CADA cliente: el bucle mantiene el servidor vivo
            while (true) {
                try {
                    // Paquete nuevo en cada vuelta para que getLength() sea correcto
                    DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                    udpSocket.receive(paquete); // bloquea hasta que llega un aviso
                    atenderCliente(paquete, LocalDateTime.now());
                } catch (IOException e) {
                    // Un fallo con un cliente NO tumba el servidor: se avisa y se sigue
                    System.out.println("Error recibiendo el aviso: " + e.getMessage());
                }
            }
        } catch (SocketException e) {
            // Solo ocurre al abrir el puerto (por ejemplo, 2705 ocupado)
            System.out.println("No se pudo abrir el puerto UDP " + UDP_PORT + ": " + e.getMessage());
        }
    }

    // ===== FASE 1: interpretar el aviso y abrir la conexión TCP =====
    private static void atenderCliente(DatagramPacket paquete, LocalDateTime recepcion) {
        // 1. Bytes -> String (solo los bytes realmente recibidos)
        String mensaje = new String(paquete.getData(), 0, paquete.getLength(), StandardCharsets.UTF_8);

        // 2. Separar y validar el formato: [puerto, nombre, fecha]
        String[] partes = mensaje.split(SEPARADOR);
        if (partes.length != 3) {
            System.out.println("Formato inválido: " + mensaje);
            return; // return en un método = abandonar este cliente, el bucle sigue
        }

        // 3. Convertir el puerto (viene DENTRO del texto, no es paquete.getPort())
        int puerto;
        try {
            puerto = Integer.parseInt(partes[0]);
        } catch (NumberFormatException e) {
            System.out.println("Puerto no numérico: " + partes[0]);
            return;
        }
        String nombre = partes[1];
        String fechaCliente = partes[2];

        // 4. Aviso por pantalla: nombre, fecha de recepción y fecha enviada por el cliente
        System.out.println("Conexión recibida de " + nombre
                + " | recibida: " + recepcion
                + " | enviada por el cliente: " + fechaCliente);

        // 5. Conectar por TCP al cliente (él ya está escuchando)
        try (Socket socketTCP = new Socket(paquete.getAddress(), puerto)) {
            LocalDateTime inicio = LocalDateTime.now(); // inicio: tras crear el Socket
            System.out.println("Conectado con " + nombre);
            try {
                leerDatos(socketTCP);
            } finally {
                // El log se escribe SIEMPRE, aunque la lectura falle
                guardarLog(nombre, puerto, inicio, LocalDateTime.now());
            }
        } catch (IOException e) {
            System.out.println("Error en la conexión TCP con " + nombre + ": " + e.getMessage());
        }
    }

    // ===== FASE 2: leer datos hasta que el cliente cierre =====
    private static void leerDatos(Socket socketTCP) throws IOException {
        socketTCP.setSoTimeout(TIMEOUT_MS); // se pone UNA vez, antes de leer
        DataInputStream in = new DataInputStream(socketTCP.getInputStream());
        try {
            while (true) {
                System.out.println(in.readUTF()); // pareja de writeUTF del cliente
            }
        } catch (EOFException e) {
            // Fin NORMAL: el cliente cerró su socket. No es un error.
            System.out.println("Fin de los datos.");
        }
    }

    // ===== LOG: añade una línea CSV nom;port;inicio;fin =====
    private static void guardarLog(String nom, int port,
            LocalDateTime inicio, LocalDateTime fin) {
        String linea = String.format("%s;%d;%s;%s%n", nom, port, inicio, fin);
        try {
            Files.writeString(Paths.get(ARCHIVO), linea,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("No se pudo escribir el log: " + e.getMessage());
        }
    }
}
