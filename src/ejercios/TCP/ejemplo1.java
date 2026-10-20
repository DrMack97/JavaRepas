package ejercios.TCP;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ejemplo1 {
    // TCP (Transmission Control Protocol)
    public static void main(String[] args) {
        
        try {
            ServerSocket server = new ServerSocket(10050);
            Socket con = server.accept();
            System.out.println("Client connected");

            DataInputStream in = new DataInputStream(new BufferedInputStream(con.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(con.getOutputStream()));
            
            
            System.out.println(in.readUTF());

            out.writeUTF("Hola");
            out.flush();
            Thread.sleep(1000);

            con.shutdownInput();
            
            con.close();
        } catch (IOException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
