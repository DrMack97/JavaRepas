package ejercios.TCP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

import javax.imageio.IIOException;

public class pong1 {
    public static void main(String[] args) throws IIOException {

        try  (DatagramSocket skt = new DatagramSocket(25565)){
            
            InetAddress ip = InetAddress.getByName("192.168.128.85");
            byte[] buffer = new byte[65156];

            while (true) {
                System.out.println("wait for server socket");
                DatagramPacket pong = new DatagramPacket(buffer, buffer.length);

                skt.receive(pong);

                //send

                byte[] data = "PONG".getBytes(StandardCharsets.UTF_8);
                DatagramPacket pkt = new DatagramPacket(data, data.length, pong.getAddress(), 
                pong.getPort()); 

                skt.send(pkt);
            }

        } catch (IOException e) {
            System.out.println("Invalid Ip");
        }
    }

}
