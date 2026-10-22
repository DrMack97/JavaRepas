package ejercios.TCP;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class MFTPPserv {
    public static void main(String[] args) {
        //init
        try (DatagramSocket socket = new DatagramSocket(2705);) {
            LOG = new File("MFTPP.log");

            while (true) {
                DatagramPacket msg = new DatagramPacket(buffer, buffer.length);
                socket.receive(msg);
                System.out.println("UDP received");
                String data = new String(msg.getData(),0,msg.getLength(), StandardCharsets.UTF_8);

                String[] parts = data.split(",");

                //TCP
                Socket TCP = new Socket(msg.getAddress(),Integer.parseInt(parts[0]));
                DataInputStream in = new DataInputStream(new BufferedInputStream(TCP.getInputStream()));
                LocalDateTime timeInit= LocalDateTime.now();
                System.out.println("TCP send");
                TCP.setSoTimeout(1000);
                try  {
                    while (true) { //falta algo 
                        System.out.println(in.readUTF());
                    }
                } catch (EOFException e) {
                    TCP.close();
                    //Escribir LOG 
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
