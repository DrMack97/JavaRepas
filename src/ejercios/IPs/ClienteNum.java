package ejercios.IPs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class ClienteNum {
    
    public static void main(String[] args) {

        /*
                                        Cliente
         */
              
        try (DatagramSocket envio = new DatagramSocket();){
            InetAddress server = InetAddress.getLocalHost();

            envio.setSoTimeout(5000);
            
            //1. envia star
            byte[] start = "Start".getBytes(StandardCharsets.UTF_8);

            //2. envio:
            envio.send(new DatagramPacket(start, start.length, server, 22000));
            //buffer / tamano / ipServer/ puerto

            //3. bucle de recepcion
            byte[] buffRecive = new byte[1024];
            for (int i = 0; i < 20; i++) {
                DatagramPacket p = new DatagramPacket(buffRecive, buffRecive.length);
                
                // soliciona el problema de paquete vacio
                try {
                    envio.receive(p);
                } catch (SocketTimeoutException e) {
                    System.out.println(" paquete perdido o servidor lento nose");
                    continue;
                }
                

            //4. convertir a String
            String texto = new String(p.getData(), 0, p.getLength(), StandardCharsets.UTF_8).trim();
            // new String + (byte[], bytes, int offset, int length, charset) <= orden     
            // "desEmpaqueta"

                // soliciona el problema de paquete vacio
                if (texto.isEmpty()) {
                    System.out.println("paquete vacio");
                }else{
                    int numeros;
                try {
                    // Parsear string a int

                    numeros = Integer.parseInt(texto);
                    
                    System.out.println("Recibo: "+ numeros + "Devuelvo el doble: "+(numeros*2));
                } catch (NumberFormatException e) {
                    System.out.println("dato no numero"+ texto );
                    continue;
                }
            }
        }
        } catch (UnknownHostException e) {
            System.out.println("Invalid IP");
        }catch (SocketException e) {
            System.out.println("Error at socket creation");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } 

    }

    

}

