package IPs;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ipsMain {
    public static void main(String[] args) {

        //InetAddress ip = InetAddress.getLocalHost();

        try {
            InetAddress ip = InetAddress.getByName("8.8.8.8");
            System.out.println(ip.getHostAddress());
            System.out.println(ip.getHostName());
            System.out.println(ip.getHostAddress());
            System.out.println(ip.getCanonicalHostName());
            ip.getHostName();
            ip.getCanonicalHostName();
            ip.getHostAddress();

        } catch (UnknownHostException e) {
            System.out.println("error, tu ip no es valida");
            System.out.println(e.getMessage());
        }
    }
}
