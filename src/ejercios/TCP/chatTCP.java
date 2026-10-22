package ejercios.TCP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class chatTCP {
    public static void main(String[] args) {
        /*
                TCP Servidor
         */

        int port = 26404;

        //abre puerto de escucha: 
        try (ServerSocket sSocket = new ServerSocket(port)){
            System.out.println("servidor escuchando en puerto: "+ port+"...");

        // accept() bloquea asta que cliente se conecte:
        try (Socket sk = sSocket.accept()){
            System.out.println("cliente conectado desde: "+sk.getInetAddress());

        // Canales de entrada/salida (Streams tcp)

        BufferedReader entrada = new BufferedReader(new InputStreamReader(sk.getInputStream()));
        PrintWriter salida = new PrintWriter(sk.getOutputStream(), true);

            // conversacion: (cliente habla primero!)

            String recibirMSM = entrada.readLine();
            System.out.println("Dominguez dice: "+ recibirMSM);

            // responder: 

            salida.println("hola dominguez! que tal el luninguez ");
            System.out.println("pepa responde: "+ "hola dominguez! que tal el luninguez");

            //despedida: 
            String despedida = entrada.readLine();
            System.out.println("Dominguez: "+despedida);

            salida.println("chao dominguez!");
            System.out.println("*pepa se despide*");
        

        } catch (Exception e) {
            e.printStackTrace();
        }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Fin ");
    }

}
