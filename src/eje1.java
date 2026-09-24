import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class eje1 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print(" Ingresa un numero ");
        int num = sc.nextInt();

        sc.nextLine();

        System.out.print(" ingresa una letra ");
        String letra = sc.nextLine();

        System.out.println(letra + " " + num);
        }
    }
