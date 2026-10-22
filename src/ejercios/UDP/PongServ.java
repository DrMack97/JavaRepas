package ejercios.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class PongServ {
    public static void main(String[] args) {
        try (DatagramSocket pong = new DatagramSocket(25565)){
            
            byte[] buff = new byte[1024];
            DatagramPacket init = new DatagramPacket(buff, buff.length);
            // recibe ping primero del cliente 
            pong.receive(init);

            // avergiua su ip 
            InetAddress ipPing = init.getAddress();
            int pingPort = init.getPort();

            System.out.println("Cliente registrado: " + ipPing + ":" + pingPort);
            
            //BUCLE PING-PONG
            while (true) {
                pong.setSoTimeout(5000);
                byte[] pelota = "Pong".getBytes(StandardCharsets.UTF_8);
                DatagramPacket p = new DatagramPacket(pelota, pelota.length, ipPing, pingPort);

                pong.send(p);

                // recibir pings
                
                byte[] recibirPing =new byte[1024];

                DatagramPacket pingR = new DatagramPacket(recibirPing, recibirPing.length,ipPing,pingPort);
                pong.receive(pingR);

                String pingg = new String(
                pingR.getData(),
                0,
                pingR.getLength(),
                StandardCharsets.UTF_8
                );
                
                System.out.println(pingg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
