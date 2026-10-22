package ejercios.TCP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class MFTPPclient {
    public static final int PORT = 28500;
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            byte[] data = (String.valueOf(PORT)+",ProfePC"+ LocalDateTime.now().toString()).getBytes(StandardCharsets.UTF_8);

            ServerSocket TCP = new ServerSocket(PORT);
            DatagramPacket msg = new DatagramPacket(data, data.length,ip,2705);

            socket.send(msg);
            Socket con = TCP.accept();
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(con.getOutputStream()));
           //ataInputStream in = new DataInputStream(new BufferedInputStream(con.getInputStream()));
            out.write("");

        } catch (SocketException e) {
            System.out.println("Error at Socket Creation");
        }catch(UnknownHostException e){
            throw new RuntimeException(e);
        }catch(IOException e){
            throw new RuntimeException();
        }
    }

}
