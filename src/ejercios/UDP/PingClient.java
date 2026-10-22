package ejercios.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class PingClient {
    public static void main(String[] args) {
        try (DatagramSocket ping = new DatagramSocket()){
            InetAddress pongServer = InetAddress.getByName("127.0.0.1");

            ping.setSoTimeout(5000);

            byte[] game = "start.Game".getBytes(StandardCharsets.UTF_8);

            ping.send(new DatagramPacket(game, game.length, pongServer, 25565));
            
            //recibir:
            byte[] recibirPongs = new byte[1024];
            
            while (true) {
                
                DatagramPacket p = new DatagramPacket(recibirPongs, recibirPongs.length);

                ping.receive(p);

                //entonces claro recibes bytes que finalmente lo que puede viajar
                //no, no recibes strings
                //bytes -> strings
                String pongReceive = new String(p.getData(),0,p.getLength(),StandardCharsets.UTF_8);
                
                System.out.println(pongReceive);

                //Enviamos Ping
                byte[] pings = "ping".getBytes(StandardCharsets.UTF_8);
                ping.send(new DatagramPacket(
                    pings,
                    pings.length,
                    pongServer,
                    25565));
            }
        } catch( SocketException e){
            System.out.println("Invalid ip ");
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

}
