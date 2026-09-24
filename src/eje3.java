import java.util.Scanner;

public class eje3 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("dame un num");

        int vecesX = sc.nextInt();
        int targetY = sc.nextInt();

        for (int i = 0; i <= vecesX; i++) {
            System.out.print(targetY);
        }
    }
}
