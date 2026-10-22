package ejercios.TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class loginServer {

    //Varios Clientes:

    private static final Set<String> clientes_Validos = 
    new HashSet<>(Arrays.asList("ana", "luis", "pedro"));

    private static final int PORT = 27010;
    private static final String ARCHIVO_LOG = "logs.log";
    private static final long SEG = 300; // 5MIN
    
    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            
        
            System.out.println("Servidor del login escuchado en puerto: "+ PORT);

            while (true) {// acepta multiples clientes ( 1 a la vez)
                try (Socket socket = serverSocket.accept()){
                    System.out.println("\n new connections from: "+ socket.getInetAddress()+ "....");

                    //canales de entrada/salida
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                    //recibir login

                    String line = input.readLine();
                    if(line == null){
                        output.println("Error: peticion vacia");
                        continue;
                    }

                    System.out.println("Peticion recibida: "+line);

                    // Procesar Login
                    String[] parts =  line.split("\\|");
                    String user = parts.length > 0 ? parts[0].trim() : ""; // ternario
                    String userHour = parts.length > 1 ? parts[1].trim() : "";

                    String resultado; 
                    String motivo;

                    if (!clientes_Validos.contains(user)) {
                        resultado = "DENEGADO";
                        motivo = "User Unknown";
                    }else{
                        try {
                            Instant horaClient = Instant.parse(userHour);

                            Instant now = Instant.now();
                            long diferencia = Math.abs(Duration.between(horaClient, now).getSeconds());

                            if (diferencia <= SEG) {
                                resultado = "ok";
                                motivo = "hora valida (desfase "+diferencia+"s)";
                            }else{
                                resultado = " Denegado";
                                motivo ="hora fuera de la tolerada";
                            }
                            
                        } catch (Exception e) {
                            resultado ="DENEGADO";
                            motivo = "Formato hora invalido";
                        }        
                    }
                    String respuesta = resultado + " - "+ motivo;
                        output.println(respuesta.trim());

                        System.out.println("Respueta enviada:"+respuesta);

                        //Guardar el archivo en un log
                        saveLog(socket.getInetAddress().toString(),user, userHour, resultado, motivo);
                }
            }
            
        } 
    }
    private static void saveLog(String ip, String user, String horaCliente, 
                                String resultado, String motivo) throws IOException{

        String LogLine = String.format("%s | IP=%s | user=%s | horaCliente=%s, | resultado=%s | motivo=%s%n", 
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            ip, user, horaCliente, resultado, motivo); //format

        Files.writeString(Paths.get(ARCHIVO_LOG), LogLine, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        System.out.println("Log guardado en : "+ ARCHIVO_LOG);
    }
}
