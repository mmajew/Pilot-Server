package main;

import tools.AddressAssistant;
import tools.ServerLogger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TCPServer extends Thread {
    final public static int SERVER_PORT = 4444;
    private boolean isRunning = false;
    private PrintWriter messageWriter;
    final MessageReceiver messageReceiver;

    public static void main(String[] args) {
        new MainFrame();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public TCPServer() {
        this.messageReceiver = new MessageReceiver();

        ServerLogger.logMessage(AddressAssistant.getLocalAddress());
    }

    public void sendMessage(String message){
        if (messageWriter != null && !messageWriter.checkError()) {
            messageWriter.println(message);
            messageWriter.flush();
        }
    }

    @Override
    public void run() {
        try {
            ServerLogger.logMessage("Oczekiwanie na połączenie.");

            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            Socket client = serverSocket.accept();

            ServerLogger.logMessage(serverSocket.getLocalSocketAddress().toString());
            ServerLogger.logMessage(AddressAssistant.getExternalAddress());

            try {
                messageWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String connectionRequest = inputBuffer.readLine();
                String clientAddress = client.getRemoteSocketAddress().toString();

                if(messageReceiver.validateConnectionRequest(connectionRequest, clientAddress)) {
                    ServerLogger.logMessage("Połaczono z " + clientAddress);
                    isRunning = true;
                    while (isRunning) {
                        String message = inputBuffer.readLine();
                        if (message != null) {
                            messageReceiver.receiveMessage(message);
                        }
                    }
                }
                else {
                    ServerLogger.logMessage("Odrzucono połączenie z " + clientAddress);
                }

            } catch (IOException exception) {
                Logger.getLogger(TCPServer.class.getName())
                        .log(Level.SEVERE, "Wystąpił błąd I/O pomiędzy klientem a serwerem.", exception);
            } finally {
                isRunning = false;
                client.close();
            }
        } catch (IOException exception) {
            Logger.getLogger(TCPServer.class.getName())
                    .log(Level.SEVERE, "Wystąpił błąd połączenia.", exception);
        }

    }

    public void close() {
        isRunning = false;
    }
}
