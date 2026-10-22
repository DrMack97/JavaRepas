package ejercios.TCP;

import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class UDP_TCPserver {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: UDP_TCPserver");
            return;
        }

        int UDPport = Integer.parseInt(args[0]);
        int TCPport = UDPport + 1;

        //inicio UDP

        try (DatagramSocket udpSocket = new DatagramSocket(UDPport)) {
            System.out.println("servidor UDP escuchando en puerto: "+ UDPport);

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            udpSocket.receive(packet);

            String msg = new String(packet.getData(), 0, packet.getLength()).trim();
            InetAddress ipClient = packet.getAddress();
            int puertoClient = packet.getPort();

            System.out.println("Recibido de la ip:"+ ipClient+" del puerto:"+puertoClient+ " -> - "+msg + " - ");

            int portRecibido = Integer.parseInt(msg);
            if (portRecibido != UDPport) {
                System.out.println("el cliente dijo otro puerto " + portRecibido);

            }
            udpSocket.close();

            // abrir canal TCP 
            System.out.println("Abriendo TCP Server");
            try (ServerSocket tcpServer = new ServerSocket(TCPport)){
                System.out.println("Servidor TCP esperado conexion con cliente ");
                
                try (Socket socket = tcpServer.accept()) {
                    System.out.println("El cliente se a conectado desde: "+socket.getInetAddress());


                    // ENVIAR bytes: 
                    String respuesta= "netfone gif you up";

                    OutputStream out = socket.getOutputStream();
                    out.write(respuesta.getBytes("UTF-8"));
                    out.flush();

                    System.out.println("[TCP] enviados:" + respuesta.getBytes("UTF-8").length + " bytes");

                } 
            } 

            System.out.println("Servidor finalizado");
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
