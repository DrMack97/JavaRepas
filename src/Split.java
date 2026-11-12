/**
 * Split
 */
public class Split {

    public static void main(String[] args) {
        String line1 = "SHOW";
        String line2 = "PUT Yellow_Coldplay";
        String line3 = "PUT";
        String line4 = "PUT Yellow";
        String line5 = "show";
        String[] lineas = { line1, line2, line3, line4, line5 };

        for (int i = 0; i < lineas.length; i++) {
            String[] mostrar = parsear(lineas[i]);

            System.out.println("Comando> " + mostrar[0] + " Cancion> " + mostrar[1] + " Autor> " + mostrar[2]);
        }

    }

    /**
     * parsear divide un frase que viene junta a 3 variables que nos indican >
     * >Comando<
     * >Cancion<
     * >Autor<
     * 
     * @param line es la frase que divide parsear
     * @return devuelve un array de String[] compuesto de variables utiles para el
     *         programa
     * 
     */
    public static String[] parsear(String line) {

        String[] comandYResto = line.split(" ", 2); // separamos line en 2 partes:

        String comando = comandYResto[0]; // para el comando:

        String cancion = comandYResto.length > 1 ? comandYResto[1] : ""; // para la cancion:

        String[] cancionYautor = cancion.split("_", 2); // para el autor (si hay)
        cancion = cancionYautor[0];
        String autor = cancionYautor.length > 1 ? cancionYautor[1] : "";

        // devolvemos un array de String[] con las 3 variables

        return new String[] { comando, cancion, autor };

    }

    /*
     * public static String[] parsear(String line) {
     * String[] partes = line.split(" ", 2);
     * String comando = partes[0];
     * String cancion = null;
     * String autor = null;
     * 
     * if (partes.length > 1 && !partes[1].isEmpty()) { // solo si hay argumento
     * String[] cancionYAutor = partes[1].split("_", 2);
     * cancion = cancionYAutor[0];
     * if (cancionYAutor.length > 1) { // solo si hay '_'
     * autor = cancionYAutor[1];
     * }
     * }
     * return new String[] { comando, cancion, autor };
     * }
     */

    /*
     * public static String[] parsear(String linea){
     * linea = "una_palabra no dice nada";
     * 
     * String[] parts = linea.split(" ");
     * 
     * String palabra1 = parts[0];
     * String no = parts[1];
     * String dice = parts[2];
     * String nada = parts[3];
     * 
     * }
     */
}
