package ejercios.TCP;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class MFTPPclient {
    public static final int PORT = 28500;
    public static final String localHost = "127.0.0.1";
    public static final  String nom = ",nombre,";
    public static void main(String[] args) {

        // Una Conexion UDP:
        try (DatagramSocket udpSocket = new DatagramSocket()) {

            InetAddress ip = InetAddress.getByName(localHost);
            LocalDateTime time = LocalDateTime.now();
            // que envie: [port, nom, data] 
            byte[] aviso = (PORT+nom+time).getBytes(StandardCharsets.UTF_8);
            
            try (ServerSocket s = new ServerSocket(PORT)) { //Preparar la escucha TCP
                s.setSoTimeout(5000);
                System.out.println("Se envia aviso: "+ new String(aviso, StandardCharsets.UTF_8));
                
                udpSocket.send(new DatagramPacket(aviso, aviso.length, ip, 2705));
                
                try (Socket conect = s.accept()) { // se establece la conexion TCP 
                    System.out.println("Servidor TCP conectado ");
                    DataOutputStream out = new DataOutputStream(conect.getOutputStream());

                    for (int i = 0; i < 5; i++) {
                        out.writeUTF("mensaje "+ i);
                    }
                    
                } catch (SocketTimeoutException e) {
                    System.out.println("El servidor no se conectó en 5 segundos "+e.getMessage());
                }
                

            } catch (IOException e) {
                System.out.println("No se pudo abrir el puerto 28500: "+e.getMessage());
            }
           

        } catch (IOException e) {
            System.out.println("No se pudo enviar el datagrama: "+e.getMessage());
        }
    }

}
