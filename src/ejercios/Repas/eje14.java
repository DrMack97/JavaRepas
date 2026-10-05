package ejercios.Repas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class eje14 {
    /*
     * 14) Realizar un programa que escriba datos de los estudiantes (nombre,
     * apellido y DNI) en formato CSV en
     * un archivo de texto.
     */
    static Scanner sc = new Scanner(System.in);

    static class Estudiante {
        String nombre;
        String apellido;
        String dni;

        public Estudiante(String nombre, String apellido, String dni) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.dni = dni;
        }
    }

    static File archivo = new File("archivo.csv");
    static boolean existe = archivo.exists();
    static ArrayList<Estudiante> listaEstudiantes;

    public static void main(String[] args) {
        listaEstudiantes = addEstudiante();
        escribirAlumnos(listaEstudiantes);
    }

    public static ArrayList<Estudiante> addEstudiante() {
        
        ArrayList<Estudiante> es = new ArrayList<>();
        String aceptar = "";

        do {

            System.out.println("Agregar nuevo estudiante: (y/n)");
            aceptar = sc.nextLine().trim();

            if (aceptar.equalsIgnoreCase("y")) {

                String nombre = "", apellido = "", dni = "";
                System.out.print("Nombre: ");
                nombre = sc.nextLine();
                System.out.print("Apellido: ");
                apellido = sc.nextLine();
                System.out.print("DNI: ");
                dni = sc.nextLine();

                es.add(new Estudiante(nombre, apellido, dni));

            } else {
                System.out.println("adios!!");
            }

        } while (aceptar.equalsIgnoreCase("y"));

        return es;
    }

    public static void escribirAlumnos(ArrayList<Estudiante> listaEstudiantes){

        try (PrintWriter salida = new PrintWriter(new FileWriter(archivo, true))) {

            if (!existe) {
                salida.println("Nombre-Apellido-DNI ");
            }

            for (Estudiante e : listaEstudiantes) {
                salida.println(e.nombre + "," + e.apellido + "," + e.dni);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
