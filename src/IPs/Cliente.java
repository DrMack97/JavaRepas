package IPs;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cliente {
    public static void main(String[] args) {
        try {
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            DatagramSocket clientSocket = new DatagramSocket();
            byte[] data = "hola".getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(data, data.length, ip, 22000);
            // Mensaje, tamaño, ip, puerto
            clientSocket.send(packet);


            // mensaje server a cliente:
            byte[] buffer = new byte[65156];
            DatagramPacket servidorCliente = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(servidorCliente);
            System.out.println("Data: "+ new String(servidorCliente.getData()));
            clientSocket.close();

        }catch (UnknownHostException e){
            System.out.println("Invalid IP");
        } catch (SocketException e) {
            System.out.println("Error at socket creation");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
