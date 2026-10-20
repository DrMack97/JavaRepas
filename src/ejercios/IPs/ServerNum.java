package ejercios.IPs;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class ServerNum {
    private static Random rnd = new Random();
    public static void main(String[] args) {

            /*
                                        Servidor
            */
            //Mercanismo de transporte que permite enviar y recibir paquetes UDP
        try (DatagramSocket socket = new DatagramSocket(22000)) {
            //(es el punto de enlace que gestiona las operaciones SEND Y RECEIVE)

            // 1. Esperar el "start" del cliente
            byte[] buf = new byte[1024];
            
            DatagramPacket inicio = new DatagramPacket(buf, buf.length);
            // es el sobre que almacena la direccion del paquete(ip y destino/origen)
            socket.receive(inicio);

            // Resolver nombre → IP
            InetAddress ipCliente = inicio.getAddress();

            int puertoCliente = inicio.getPort();
            System.out.println("Cliente registrado: " + ipCliente + ":" + puertoCliente);


            // 2. Bucle de envío
            for (int i = 0; i < 20; i++) {  // o while(true)
                // crea numero ramdon: 
                int numero = rnd.nextInt(100) + 1;

                
                // uso perdido para simular una perdida de paquetes:
                if(perdido()){
                    System.out.println("PACKAGE PERDIDO! "+ numero);
                    continue;
                }
                // empaqueta y envia (envia el numero que haya salido )
                byte[] data = String.valueOf(numero).getBytes(StandardCharsets.UTF_8);
                DatagramPacket p = new DatagramPacket(data, data.length, ipCliente, puertoCliente);
                //envia: 
                socket.send(p);

                System.out.println("Enviado: ("+numero+")" );

                Thread.sleep(500); // opcional, para ver el flujo
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static public Boolean perdido(){
        // probabilidad 25%
        Boolean paquete = false;
        double probabilidad = rnd.nextDouble();
        if(probabilidad < 0.25){
            paquete = true;
        }else{
            System.out.println("enviado");
        }
        
        return paquete;
    }

    /*
    static public void envioContador(){
        int count = 0;

        count++;
        if (count % 4 ==0) {
            System.out.println("paquete perdido ");
        }else{
            System.out.println("enviado: "+count);
        }
    }
        */

}
