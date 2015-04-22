package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;


public class AddressAssistant {
    private final String ADDRESS_ERROR = "Nie powiodło się ustalenie adresu serwera.";
    private final String IP_ADDRESS = "Adres IP serwera: ";
    private final String LOCAL_ADDRESS = "Lokalny adres IP serwera: ";
    private final String EXTERNAL_ADDRESS = "Zewnętrzny adres IP serwera: ";

    public String getAddresses() {
        String result;

        String localAddress = getLocalAddress();
        String externalAddress = getExternalAddress();

        if(localAddress.equals("")) result = ADDRESS_ERROR;
        else if(localAddress.equals(externalAddress)) result = IP_ADDRESS + localAddress;
        else if(externalAddress.equals("")) result = LOCAL_ADDRESS + localAddress;
        else result = LOCAL_ADDRESS + localAddress + "\n" + EXTERNAL_ADDRESS + externalAddress;

        return result;
    }

    private String getLocalAddress() {
        String result = "";

        try {
            result += InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {}

        return result;
    }

    private String getExternalAddress() {
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
