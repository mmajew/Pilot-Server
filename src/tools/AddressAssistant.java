package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;


public class AddressAssistant {
    private static final String addressError = "Nie powiodło się ustalenie adresu serwera.";

    public static String getAddresses() {
        String result;

        String localAddress = getLocalAddress();
        String externalAddress = getExternalAddress();

        if(localAddress.equals("")) result = addressError;
        else if(localAddress.equals(externalAddress)) result = "Adres IP serwera: " + localAddress;
        else result = "Lokalny adres IP serwera: " + localAddress + " \nZewnętrzny adres IP serwera: " + externalAddress;

        return result;
    }

    private static String getLocalAddress() {
        String result = "";

        try {
            result += InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {}

        return result;
    }

    private static String getExternalAddress() {
        String result = "";

        try {
            URL url = new URL("http://checkip.amazonaws.com");
            URLConnection connection = url.openConnection();

            connection.setConnectTimeout(1500);
            connection.setReadTimeout(1500);

            result += new BufferedReader(new InputStreamReader(
                    connection.getInputStream())).readLine();

        } catch (IOException ignored) {}

        return result;
    }
}
