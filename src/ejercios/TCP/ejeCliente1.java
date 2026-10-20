package ejercios.TCP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ejeCliente1 {
    public static void main(String[] args) {
        try {
            Socket skt = new Socket("localhost",10050);
            System.out.println("Conectado al servidor");

            DataInputStream in = new DataInputStream(new BufferedInputStream(skt.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(skt.getOutputStream()));
            
            out.writeUTF("soy cliente");
            out.flush();
            System.out.println("message complete");

            String respuesta = in.readUTF();
            System.out.println("Servidor dice: "+ respuesta);

            skt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
