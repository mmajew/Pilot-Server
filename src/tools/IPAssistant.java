package tools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;


public class IPAssistant {
    private static final String localAddressError = "Nie udało się ustalenie adresu lokalnego serwera.";
    private static final String externalAddressError = "Nie udało się ustalenie adresu zewnętrznego serwera.";


    public static String getLocalAddress() throws Exception{
        String result = "Lokalny adres IP serwera: ";

        try{
            result += InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException exception) {
            result = localAddressError;
        }

        return result;
    }


    public static String getExternalAddress() throws Exception{
        String result = "Zewnętrzny adres IP serwera: ";

        try{
            URL url = new URL("http://checkip.amazonaws.com");
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(1500);
            connection.setReadTimeout(1500);
            result += connection.getInputStream().read();
        } catch (IOException exception) {
            result = externalAddressError;
        }

        return result;
    }
}
