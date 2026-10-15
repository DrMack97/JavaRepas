package ejercios.IPs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

public class enviaNumeros {
    private static Random rnd = new Random();
    public static void main(String[] args) {
              
        try (DatagramSocket envio = new DatagramSocket(22000);){
            // esperar al cliente: 
            byte[] buffer = new byte[1024];
            DatagramPacket inicio = new DatagramPacket(buffer, buffer.length);
            envio.receive(inicio);

            InetAddress ip = InetAddress.getByName("127.0.0.1");
            int clientPort = inicio.getPort();
            System.out.println("Cliente registrado: "+ip+":"+clientPort);

        for (int i = 0; i < 20; i++) {
            int num = rnd.nextInt(100)+1;
            envioContador();
        }
            
        } catch (UnknownHostException e) {
            System.out.println("Invalid IP");
        }catch (SocketException e) {
            System.out.println("Error at socket creation");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    static public Boolean moneda(){
        Boolean paquete = false;
        double probabilidad = rnd.nextDouble();
        if(probabilidad < 0.25){
            paquete = true;
            System.out.println("paquete perdido");
        }else{
            System.out.println("enviado");
        }
        
        return paquete;
    }

    static public void envioContador(){
        int count = 0;

        count++;
        if (count % 4 ==0) {
            System.out.println("paquete perdido ");
        }else{
            System.out.println("enviado: "+count);
        }
    }

}

