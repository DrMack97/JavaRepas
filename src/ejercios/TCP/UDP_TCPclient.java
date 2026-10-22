package ejercios.TCP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class UDP_TCPclient {

    public static void main(String[] args) {
        
        if (args.length < 1 ) {
            System.out.println("Client UDP_TCP");
            return;
        }
        int puertoUDP = Integer.parseInt(args[0]);
        int puertoTCP = puertoUDP +1;

        String host = "localhost";


        // UDP 

        try (DatagramSocket udpSocket = new DatagramSocket()){
            InetAddress server = InetAddress.getByName(host);

            byte[] datos = String.valueOf(puertoUDP).getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(datos, datos.length, server, puertoUDP);

            System.out.println("UDP enviando a" + puertoUDP + "desde "+ host);

            udpSocket.send(packet);

            udpSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // tcp en puerto + 1

        System.out.println("[TCP] conectado a "+host+ "> "+puertoTCP+"...");

        try (Socket socket = new Socket(host, puertoTCP)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), "UTF-8"));
            
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println("TCPserver recibio : "+ line);
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }

        System.out.println("Cliente finalizado");
    }

}
