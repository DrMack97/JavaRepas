package ejercios.TCP;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Random;

public class IPcheck {

    /*
     * Incompleto
     */
    public static void main(String[] args) {
        Random rnd = new Random();
        
        try  (Socket s = new Socket("192.189.212.90", 24312)){
            
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));

            for (int i = 0; i < 4; i++) {
                int randomNumber = rnd.nextInt(0, 256);

                System.out.println("Cliente envia un entero>" + randomNumber);

                out.write(randomNumber); // es mas optimo porque envia un solo byte
            }
                out.flush();
            s.setSoTimeout(1000);
            DataInputStream in = new DataInputStream(s.getInputStream());
            String respuesta = in.readUTF();
            if (respuesta.equalsIgnoreCase("true")) {
                System.out.println("Los numeros dan como resultado una direccion IP");
            }else if(respuesta.equalsIgnoreCase("false")) {
                System.out.println("Los números no forman una IP válida");
            }else{
                System.out.println("la informacion no coincide con la esperada: " +respuesta);
            }
            

        } catch (SocketTimeoutException e){
            System.out.println("El servidor no respondió en 1 segundo. Inténtalo de nuevo más tarde.");
        } catch (UnknownHostException e){
            System.out.println("No se pudo resolver la dirección del servidor: " + e.getMessage()); 
        } catch (EOFException e){
            System.out.println("El servidor cerró la conexión sin enviar respuesta.");
        } catch (IOException  e){
            System.out.println("Error de comunicación con el servidor: " + e.getMessage());
        }
    }
}
