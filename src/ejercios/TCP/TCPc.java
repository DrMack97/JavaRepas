package ejercios.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class TCPc {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) {

        try (Socket socket = new Socket(HOST, PORT)) { // 1. conectar (handshake aquí)
            socket.setSoTimeout(3000); // 2. antes de leer
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF("hola"); // 3. enviar
            System.out.println(in.readUTF()); // 4. leer respuesta
        } // 5. cierre automático -> EOF en el otro lado
        catch (ConnectException e) { // hija: antes de IOException
            System.out.println("Servidor caído o puerto cerrado");
        } catch (SocketTimeoutException e) {
            System.out.println("El servidor no respondió a tiempo");
        } catch (UnknownHostException e) {
            System.out.println("Host no encontrado");
        } catch (IOException e) {
            System.out.println("Error de comunicación: " + e.getMessage());
        }
    }
}
