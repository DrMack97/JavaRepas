package ejercios.TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.Instant;

public class loginClient {

    public static void main(String[] args) throws UnknownHostException, IOException {
        String host = "localhost";
        int port = 27010;

        String user = "luis"; // 

        String localHour = Instant.now().toString();

        System.out.println("Cliente user: "+ user);
        System.out.println("hora actual: "+localHour);

        //Conectar al servidor y hacer peticion de login:
        try (Socket socket = new Socket(host, port)) {

            PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String peticion = user + "|"+localHour;
            output.println(peticion);

            // leer respuesta del servidor: 
            String respuesta = input.readLine();
            System.out.println("Servidor: "+ respuesta);

            // interpretar respuesta: 
            if (respuesta != null && respuesta.startsWith("ok")) {
                System.out.println("Login exitoso!");
            } else{
                System.out.println("Login Rechazado");
            }

        } 
    }

}
