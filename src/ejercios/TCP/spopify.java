package ejercios.TCP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class spopify {

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(25559)) {
            ArrayList<String> songs = canciones();
            boolean servidorActivo = true;
            
            while (servidorActivo) {

                try (Socket con = server.accept()) {
                    con.setSoTimeout(5000);
                    DataOutputStream out = new DataOutputStream(new BufferedOutputStream(con.getOutputStream()));
                    DataInputStream in = new DataInputStream(new BufferedInputStream(con.getInputStream()));

                    // Leer peticion con tiempo de espera:

                    String peticion = in.readUTF();
                    
                    String comando = "";
                    String cancion  = "";
                    
                    if (comprobarPeticion(peticion)) {
                        comando = separarComando(peticion)[0];
                        cancion = separarComando(peticion)[1];
                    }
                    
                    // cumplir orden

                    switch (comando) {
                        case "SHOW":

                            if (!songs.isEmpty()) {
                                String lista = String.join(", ", songs);
                                out.writeUTF(lista);
                            }else{
                                out.writeUTF("Empty");
                            }
  
                            break;
                        case "PUT":

                            if (!songs.contains(cancion) && !cancion.isBlank()) {
                                
                                songs.add(cancion);
                                out.writeBoolean(true);
                            }else{
                                out.writeBoolean(false);
                            }
                            

                            break;
                        case "REMOVE":

                            if (songs.contains(cancion)) {
                                songs.remove(cancion);
                                out.writeBoolean(true);
                            } else {
                                out.writeBoolean(false);
                            }
                            
                            break;
                        case "SHUTDOWN":
                            out.writeUTF("Apagant...");
                            servidorActivo = false;
                            break;

                        default:
                            out.writeUTF("ERROR: UNKNOWN COMMAND");
                            break;
                    }
                    out.flush();

                } catch (SocketTimeoutException e) {
                    System.out.println("El cliente no responde");
                } catch (EOFException e) {
                    System.out.println("cliente se cierra sin enviar nada");
                }catch (IOException e) {
                    System.out.println("conexion cortada");
                }

            }

        } catch (IOException e) {
           System.out.println("server not run");
        }
    }

    public static boolean comprobarPeticion(String peticion){
        boolean comprobar = true
        ;
        if (!peticion.isBlank() && !peticion.isEmpty()) {
            System.out.println("peticion recibida: " + peticion);

        } else {
           comprobar = false;
        }

        return comprobar;
    }

    public static String[] separarComando(String peticion) {
        String[] separar = peticion.split(" ", 2);
        String comando = separar[0];
        String cancion = "";

        if (separar.length > 1 && !separar[1].isEmpty() && !separar[1].isBlank()) {
            cancion = separar[1];
        }
        
        return new String[] {comando,cancion};
    }

    /**
     * array list de ejemplo
     * 
     * @return devuelve una array de strings para mostrar agregar y remover
     *         canciones
     */

    public static ArrayList<String> canciones() {
        ArrayList<String> lista = new ArrayList<>();
        lista.add("cancion1_autor2");
        lista.add("cancion2_autor1");
        lista.add("cancion1_autor1");
        return lista;
    }

    /*
     * public static String[] separarAutor(String cancion){
     * String[] separarAutor = cancion.split("_",2);
     * String cancionSinAutor = separarAutor[0];
     * String autor = separarAutor[0];
     * 
     * return new String[] {cancionSinAutor, autor};
     * }
     */

}
