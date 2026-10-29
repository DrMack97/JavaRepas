package ejercios.TCP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDOs {
    private static final int PORT = 2705;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {

        try (DatagramSocket socket = new DatagramSocket(PORT)) { // 1. abrir puerto fijo
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length); // 2. paquete vacío
                socket.receive(paquete); // 3. bloquea hasta que llega uno
                String texto = new String(paquete.getData(), 0,
                        paquete.getLength(), StandardCharsets.UTF_8); // 4. solo los bytes recibidos

                // Responder (opcional): al origen del datagrama
                byte[] resp = "ok".getBytes(StandardCharsets.UTF_8);
                socket.send(new DatagramPacket(resp, resp.length,
                        paquete.getAddress(), paquete.getPort())); // 5. IP y puerto de ORIGEN
            }
        } catch (SocketException e) {
            System.out.println("Puerto ocupado: " + e.getMessage()); // antes de IOException
        } catch (IOException e) {
            System.out.println("Error de red: " + e.getMessage());
        }
    }

}
