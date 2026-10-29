package ejercios.TCP;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * Cliente MFTPP.
 * 1. Abre un ServerSocket (prepara la escucha TCP ANTES de avisar).
 * 2. Envía por UDP el aviso "puerto,nombre,fecha" al servidor.
 * 3. Espera a que el servidor se conecte (accept).
 * 4. Envía datos por TCP y cierra la conexión.
 */
public class MFTPPcliente {

    // ===== CONSTANTES =====
    private static final int TCP_PORT = 28500;       // puerto donde espera al servidor
    private static final int SERVER_UDP_PORT = 2705;
    private static final String HOST = "127.0.0.1";
    private static final String NOMBRE = "nombre";
    private static final String SEPARADOR = ",";     // formato: puerto,nombre,fecha
    private static final int TIMEOUT_MS = 5000;
    private static final int NUM_MENSAJES = 5;

    public static void main(String[] args) {
        // Los dos recursos se abren juntos; ServerSocket ANTES de enviar el aviso
        try (DatagramSocket udpSocket = new DatagramSocket();
                ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {

            serverSocket.setSoTimeout(TIMEOUT_MS); // no esperar eternamente en accept()
            enviarAviso(udpSocket);

            // accept() devuelve el Socket del extremo cliente: aquí nace la conexión
            try (Socket conexion = serverSocket.accept()) {
                System.out.println("Servidor conectado");
                enviarDatos(conexion);
            } // al salir, el socket se cierra solo: el servidor recibe EOF

        } catch (SocketTimeoutException e) { // hija de IOException: va antes
            System.out.println("El servidor no se conectó en " + TIMEOUT_MS / 1000 + " segundos");
        } catch (IOException e) {
            System.out.println("Error de comunicación: " + e.getMessage());
        }
    }

    // ===== UDP: aviso de conexión =====
    private static void enviarAviso(DatagramSocket udpSocket) throws IOException {
        String mensaje = TCP_PORT + SEPARADOR + NOMBRE + SEPARADOR + LocalDateTime.now();
        byte[] datos = mensaje.getBytes(StandardCharsets.UTF_8);
        System.out.println("Se envía aviso: " + mensaje);
        udpSocket.send(new DatagramPacket(datos, datos.length,
                InetAddress.getByName(HOST), SERVER_UDP_PORT));
    }

    // ===== TCP: datos que quiera enviar =====
    private static void enviarDatos(Socket conexion) throws IOException {
        DataOutputStream out = new DataOutputStream(conexion.getOutputStream());
        for (int i = 0; i < NUM_MENSAJES; i++) {
            out.writeUTF("mensaje " + i); // pareja de readUTF del servidor
        }
    }
}