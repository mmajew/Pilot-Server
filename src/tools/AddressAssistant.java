package tools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;


public class AddressAssistant {
    private static final String localAddressError = "Nie udało się ustalenie adresu lokalnego serwera.";

    public static String getAddresses() {
        String localAddress = getLocalAddress();
        String externalAddress = getExternalAddress();
        return externalAddress.equals("") ? localAddress : localAddress + "\n" + externalAddress;
    }

    private static String getLocalAddress(){
        String result = "Lokalny adres IP serwera: ";

        try{
            result += InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException exception) {
            result = localAddressError;
        }

        return result;
    }

    private static String getExternalAddress(){
        String result = "";

        try{
            URL url = new URL("http://checkip.amazonaws.com");
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(1500);
            connection.setReadTimeout(1500);
            result += connection.getInputStream().read();
        } catch (IOException exception) {}

        return result;
    }
}
