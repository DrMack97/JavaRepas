package ejercios.TCP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class UDPc {
    private static final String HOST = "127.0.0.1";
    private static final int SERVER_PORT = 2705;

    public static void main(String[] args) {

        try (DatagramSocket socket = new DatagramSocket()) { // 1. SIN puerto: lo asigna el SO
            byte[] datos = "hola".getBytes(StandardCharsets.UTF_8); // 2. String -> bytes
            socket.send(new DatagramPacket(datos, datos.length,
                    InetAddress.getByName(HOST), SERVER_PORT)); // 3. destino: IP + puerto

            // Solo si espera respuesta:
            socket.setSoTimeout(3000); // 4. ANTES de receive
            byte[] buffer = new byte[1024];
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
            socket.receive(respuesta);
            System.out.println(new String(respuesta.getData(), 0,
                    respuesta.getLength(), StandardCharsets.UTF_8));
        } catch (SocketTimeoutException e) { // hija: va antes
            System.out.println("El servidor no respondió a tiempo");
        } catch (IOException e) {
            System.out.println("Error de red: " + e.getMessage());
        }
    }
}
