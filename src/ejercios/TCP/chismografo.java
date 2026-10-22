package ejercios.TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class chismografo {
    //una clase para connection
    static class Connection {
        Socket socket;
        BufferedReader in;
        PrintWriter out;

        
    public Connection(Socket s) throws IOException {
            this.socket = s;
            this.in = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));
            this.out = new PrintWriter(s.getOutputStream(), true);
    }
}

        //lista para puertos:
        private static final int[] PORTS = { 2002, 2003, 2004, 2005, 2006 };
        private  static final int JUMPS = PORTS.length;


    public static void main(String[] args) throws IOException {

        // abrimos los 5 server Sockets: 

        //abrir => array => accept() para cada uno

        ServerSocket[] servers = new ServerSocket[JUMPS];

        for (int i = 0; i < JUMPS; i++) {
            servers[i] = new ServerSocket(PORTS[i]);

            System.out.println("Escuchando el port: "+ PORTS[i]+"...");
        }

        // ahora guardamos los ports en una array
        List<Connection> clients = new ArrayList<>();
        // aceptar las 5 conexiones: 
        
        for (int i = 0; i < JUMPS; i++) {
            Socket s = servers[i].accept();
            Connection c = new Connection(s);
            clients.add(c);

            System.out.println(" Cliente conectado en port: "+PORTS[i]+"desde -"+s.getInetAddress());

        }

        // cerrar multiplememte serverSockets: 
        for (ServerSocket s : servers)s.close(); 

        //BUCLE PRINCIPAL 

        while (true) {
            System.out.println("== Chismografo ==");

            Connection firts = clients.get(0); //el primero del array

            String fraseOG = firts.in.readLine(); //in. de la clase Connection
            System.out.println("Frase original: "+fraseOG+"...");

            String mensaje = fraseOG;

            int anterior = 0; 
            Random rnd = new Random();

            for (int i = 1; i < JUMPS; i++) {
                
                char x = (char)(97+rnd.nextInt(26));// toma una letra aleatoria
                char y = (char)(97+rnd.nextInt(26));// la camia por una letra aleatoria

                String modificado = mensaje.replace(x, y);

                System.out.println("  Salto " + i + ": cambio '" + x
                        + "' -> '" + y + "' | mensaje: \"" + modificado + "\"");
                
                // elegir siguiente cliente al azar 

                int destino;
                do {
                    destino = rnd.nextInt(clients.size());
                } while (destino == anterior && clients.size() > 1);

                //enviar al cliente elegido y leer su respuesta:
                Connection clientDestino = clients.get(destino);// un destino al azar (1-5)
                clientDestino.out.println(modificado); //enviar 
                mensaje = clientDestino.in.readLine(); // recibir

                System.out.println("  Cliente[" + destino + "] responde: \"" + mensaje + "\"");

                 // El que acaba de hablar pasa a ser el "anterior"
                anterior = destino;
            }

            System.out.println("\n--- RESULTADO DEL JUEGO ---");
            System.out.println("Original : \"" + fraseOG + "\"");
            System.out.println("Final    : \"" + mensaje + "\"");
            System.out.println("---------------------------");

        }
    
    }

    /*
    import java.util.Random;
Random rnd = new Random();

char x = (char) ('a' + rnd.nextInt(26));
     */

}
