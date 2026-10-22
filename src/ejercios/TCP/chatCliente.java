package ejercios.TCP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class chatCliente {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 26404;

        //Dominguez inicia conexion

        try (Socket socket = new Socket(host, port)){
            System.out.println("Dominguez conectado a server pepa con "+host+" port: "+port);

            //Streams de entrada/salida
            BufferedReader entry = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            // cliente llama primero 
            out.println("ola pepa como te va esta semana?");
            System.out.println(" Domingues saluda: ");

            // server responde

            String respuesta = entry.readLine();
            System.out.println("pepa dice: "+ respuesta);

            // despedida 

            out.println("bueno mejor me voy que se hace martinguez chao!");
            System.out.println("Dominguez se despide");

            String despedida = entry.readLine();
            System.out.println("peda dice: "+ despedida);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
