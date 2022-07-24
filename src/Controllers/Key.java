package Controllers;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Key {
    public static void nor(){
        InetAddress localHost;

        {
            try {
                localHost = InetAddress.getLocalHost();
                NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
                byte[] hardwareAddress = ni.getHardwareAddress();
                String[] hexadecimal = new String[hardwareAddress.length];
                for (int i = 0; i < hardwareAddress.length; i++) {
                    hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
                }
                String macAddress = String.join("-", hexadecimal);
                System.out.println(macAddress);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

    }
}
