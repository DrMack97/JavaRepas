package ejercios.stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Serie {

    //Serializacion

    /*
    7.- Serialització

	a) Crea un objecte Alumne amb els atributs nom(String), cicle (String), edat (int). 

    b) Crea un mètode que et demani les dades de 3 alumnes i els guardi en un fitxer binari «alumnes.bin». 

    c)  Crea un altre mètode que llegeixi les dades del fitxer «alumnes.bin» i mostra-les per pantalla

     */
    // a)

    static class Alumne {
        String nom;
        String cicle;
        String edat;

        public Alumne(String nom, String cicle, String edat) {
            this.nom = nom;
            this.cicle = cicle;
            this.edat = edat;
        }
    }
    static Scanner sc = new Scanner(System.in); 

    public static void main(String[] args)  {

        ArrayList<Alumne> al = addEstudiante();

        String files =  archivoBinario(al);
        
        leerArchivo(files);
    }

    public static ArrayList<Alumne> addEstudiante() {
        
        ArrayList<Alumne> es = new ArrayList<>();
        String aceptar = "";

        do {

            System.out.println("Agregar nuevo estudiante: (y/n)");
            aceptar = sc.nextLine().trim();

            if (aceptar.equalsIgnoreCase("y")) {

                String nom = "", cicle = "", edat = "";
                System.out.print("Nom: ");
                nom = sc.nextLine();
                System.out.print("cicle: ");
                cicle = sc.nextLine();
                System.out.print("edat: ");
                edat = sc.nextLine();
                

                es.add(new Alumne(nom, cicle, edat));

            } else {
                System.out.println("adios!!");
            }

        } while (aceptar.equalsIgnoreCase("y"));

        return es;
    }

    public static String archivoBinario(ArrayList<Alumne> listaAlumnes){
        
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/alumnes.bin"))){
            
            for (Alumne al : listaAlumnes) {
                writer.print(al.nom+"/"+al.cicle+"/"+al.edat);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String file = "src/alumnes.bin";

        return file;
    }

    public static void leerArchivo(String file){
        String linea; 

        try (BufferedReader lector = new BufferedReader(new FileReader(file))){
            
            while ((linea = lector.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    
}
