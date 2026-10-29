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

public class MFTPPserv {
    private static final String ARCHIVO = "MFTPP.log";

    public static void main(String[] args) throws SocketException {

        // abir servidor escuchando:
        try (DatagramSocket udpSocket = new DatagramSocket(2705)) {

            byte[] buffer = new byte[1024];

            while (true) {
                try {
                    DatagramPacket p = new DatagramPacket(buffer, buffer.length);
                    udpSocket.receive(p);
                    String textLog = new String(p.getData(), 0, p.getLength(), StandardCharsets.UTF_8);

                    System.out.println("hemos recibido del cliente nuevo loggin: " + textLog);

                    // Procesar:
                    int clientPort = 0;
                    String[] parts = textLog.split(",");
                    if (parts.length != 3) {
                        System.out.println("Formato invalido");
                        continue;
                    } else {
                        try {
                            clientPort = Integer.parseInt(parts[0]);
                        } catch (NumberFormatException e) {
                            System.out.println("Puerto no numerico " + parts[0]);
                            continue;
                        }
                    }
                    LocalDateTime recepcion = LocalDateTime.now();
                    System.out.println(
                            "Conexión recibida de " + parts[0] + " " + parts[1] + " " + parts[2] + " " + recepcion);

                    // TCP
                    try (Socket socketTCP = new Socket(p.getAddress(), clientPort)) {
                        LocalDateTime inicio = LocalDateTime.now();
                        System.out.println("Conectado con cliente ");
                        try {
                            DataInputStream in = new DataInputStream(socketTCP.getInputStream());
                            while (true) {
                                socketTCP.setSoTimeout(5000);
                                System.out.println(in.readUTF());
                            }
                        } catch (EOFException e) {
                            System.out.println("Fin de los datos: no es un error");
                        } finally {
                            LocalDateTime fin = LocalDateTime.now();
                            guardarLog(parts[1], clientPort, inicio, fin);
                        }
                    } catch (IOException e) {
                        System.out.println("error de conexion TCP" + e.getMessage());
                    }

                } catch (SecurityException e) {
                    System.out.println("Puerto ocupado" + e.getMessage());
                } catch (IOException e) {
                    System.out.println("Conexion fallida" + e.getMessage());
                }

            }
        } // recibir log del cliente

    }

    private static void guardarLog(String nom, int port,
            LocalDateTime inicio, LocalDateTime fin) {
        // 1. Construir la línea con separador ';' y sin espacios
        String linea = String.format("%s;%d;%s;%s%n", nom, port, inicio, fin);

        // 2. Añadir al final del fichero (CREATE: lo crea si no existe)
        try {
            Files.writeString(Paths.get(ARCHIVO), linea,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("No se pudo escribir el log: " + e.getMessage());
        }
    }
}
