package ejercios.UDP;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class enviar_ip {
    public static void main(String[] args) {
        try {
            
            InetAddress ip = InetAddress.getLocalHost();
            // la dirección IP del destino.
            byte[] buffer = new byte[65156];
            DatagramSocket ServerSocket = new DatagramSocket(22000);
            // buzon por donde se envia o recibe 

            while(true){

                System.out.println("waiting for server socket");
                DatagramPacket msg = new  DatagramPacket( buffer, buffer.length );
                // el "sobre" con los datos, IP y puerto.
                ServerSocket.receive(msg);
                System.out.println("Host: " + ip.getHostName());
                System.out.println("Port: " + msg.getPort());
                System.out.println("Data: "+new String(msg.getData()));
                System.out.println("Length: "+msg.getLength());


                //Enviar respuesta al cliente::
                byte[] data = "this msg is true".getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket( data, data.length,
                msg.getAddress(), 
                msg.getPort());
                ServerSocket.send(packet);

            }
        }catch (UnknownHostException | SocketException e){
            System.out.println("error, ip no valida ");
            System.out.println(e.getMessage());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
