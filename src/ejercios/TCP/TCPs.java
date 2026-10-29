package ejercios.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPs {
    private static final int PORT = 5000;

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(PORT)) { // 1. reservar y escuchar
            while (true) {
                try (Socket cliente = server.accept()) { // 2. bloquea; devuelve el Socket del cliente
                    cliente.setSoTimeout(5000); // 3. antes de leer
                    DataInputStream in = new DataInputStream(cliente.getInputStream());
                    DataOutputStream out = new DataOutputStream(cliente.getOutputStream());

                    String recibido = in.readUTF(); // 4. pareja de writeUTF
                    out.writeUTF("Recibido: " + recibido); // 5. responder

                } catch (SocketTimeoutException e) {
                    System.out.println("El cliente no envió nada a tiempo");
                } catch (EOFException e) {
                    System.out.println("El cliente cerró la conexión");
                } catch (IOException e) {
                    System.out.println("Error con un cliente: " + e.getMessage()); // el bucle sigue
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo abrir el puerto " + PORT + ": " + e.getMessage());
        }
    }
}
