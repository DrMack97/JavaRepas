package ejercios.stream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;


public class basicStrams {

    /*      
            1.- Entrar una frase i retorna per pantalla amb System.in i System.out

            2.- Llegeix de teclat i mostra codificació entera

            3.- Mostra un fitxer de text llegint amb FileReader

            3(alt).- Mostra un fitxer de text llegint amb BufferedReader

            4.- Demana una frase per teclat i l’escriu en un fitxer

            5.- Copia el contingut d'un fitxer a un altre caràcter a caràcter

            6.- Copiar un fitxer binari (per exemple una imatge jpg)

            7.- Serialització
            	a) Crea un objecte Alumne amb els atributs nom(String), cicle (String), edat (int).
                b) Crea un mètode que et demani les dades de 3 alumnes i els guardi en un fitxer binari «alumnes.bin».
                c)  Crea un altre mètode que llegeixi les dades del fitxer «alumnes.bin» i mostra-les per pantalla
    * */
    public static void main(String[] args)  {
        
    }

    static void SystemInOut(){ // pt 1
        try 
            (
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter escrito = new BufferedWriter(new OutputStreamWriter(System.out))
            ){

            System.out.println("Escribe una frase: ");

            String frase = lector. readLine();

            escrito.write(frase);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void NombreCodificado(){ // pt 2
        try {

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        
            System.out.println("whats your name!?");
            String name = teclado.readLine();

            //convertir nombre en numero: 
            int numero = Integer.parseInt(name);
            System.out.println("nombre: (Base 10) "+ numero);

            String binario = Integer.toBinaryString(numero);
            System.out.println("nombre (binario): "+ binario);

            System.out.println("nombre: (Hexadecimal): "+ Integer.toHexString(numero));
            
        } catch(NumberFormatException e){
            System.err.println("Debes ingresar un numero");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void leerArchivo(String file){               //pt 3 (path desde /src)
        try (FileReader lector = new FileReader(file);// dos lectores para la misma actividad
            BufferedReader reader = new BufferedReader(new FileReader("src/ejercios/stream/archivo.csv"))){
            
            int caracteresLeidos;

            // read() lee un carácter a la vez y devuelve su código ASCII/Unicode.
            // Devuelve -1 cuando llega al final del archivo.
            
            while ((caracteresLeidos = lector.read()) != -1) {
                
                System.out.print((char) caracteresLeidos);
            }

            // ahora con BufferedReader 
            String line;

            // readLine() lee el archivo línea por línea hasta devolver null (fin de archivo)
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void archivoNuevo(){                 //Pt4 archivoNuevo
        Scanner sc = new Scanner(System.in);

        try (PrintWriter writer = new PrintWriter("escrito.txt")){
            
            System.out.println("escribe una frase conchale!");
            String frase = sc.nextLine();

            writer.println(frase);

        } catch (IOException e) {
            e.printStackTrace();
        }

        sc.close();
    }

    static public void copyArchivo(){           //Pt5 copiar archivo
        
        Path origen = Paths.get("src/escrito.txt");
        Path destino = Paths.get("src/nuevo_escrito.txt");

        try {
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void copyImagen(){            //Pt6 copyImagen
        String origen = "src/imagen.jpg";
        String destino = "src/copia_imagen.jpg";

        try (
            FileInputStream entrada = new FileInputStream(origen);
            FileOutputStream salida = new FileOutputStream(destino)
        ){
            //crear buffer de byts
            byte[] buffer = new byte[1024];
            int bytesLeidos;

            // read(buffer)
            while ((bytesLeidos = entrada.read(buffer)) != -1 ) {
                salida.write(buffer, 0, bytesLeidos);
            }            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    


}
