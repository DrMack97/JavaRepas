package ejercios.IPs;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class clientNumeros {

    public static void main(String[] args) {
        
        try (DatagramSocket socket = new DatagramSocket(22000)) {
            // 1. Esperar el "start" del cliente
            byte[] buf = new byte[1024];
            DatagramPacket inicio = new DatagramPacket(buf, buf.length);
            socket.receive(inicio);

            InetAddress ipCliente = inicio.getAddress();
            int puertoCliente = inicio.getPort();
            System.out.println("Cliente registrado: " + ipCliente + ":" + puertoCliente);

            Random rand = new Random();

            // 2. Bucle de envío
            for (int i = 0; i < 20; i++) {  // o while(true)
                int numero = rand.nextInt(100) + 1;

                // TODO: aplicar la pérdida del 25%
                // if (¿se pierde?) { System.out.println("PERDIDO: " + numero); continue; }

                // TODO: empaquetar y enviar
                // byte[] data = String.valueOf(numero).getBytes(StandardCharsets.UTF_8);
                // DatagramPacket p = new DatagramPacket(data, data.length, ipCliente, puertoCliente);
                // socket.send(p);
                // System.out.println("ENVIADO: " + numero);

                Thread.sleep(500); // opcional, para ver el flujo
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
