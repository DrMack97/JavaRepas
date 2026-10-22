package ejercios.TCP;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class IPcheck {

    /*
        Incompleto 
     */

    public static void main(String[] args) {
        try  {
            InetAddress ip = InetAddress.getByName("192.186.212.90");
            Socket s = new Socket(ip,24312);
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            Random rnd = new Random();

            for (int i = 0; i < 4; i++) {
                out.write(rnd.nextInt(0,255));
            }
            s.
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
