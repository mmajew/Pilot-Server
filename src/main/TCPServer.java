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
    ServerSocket serverSocket;

    public static void main(String[] args) {
        new MainFrame();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public TCPServer() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.messageReceiver = new MessageReceiver();

        ServerLogger.logMessage(AddressAssistant.getLocalAddress());
        ServerLogger.logMessage(AddressAssistant.getExternalAddress());
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
            Socket client = serverSocket.accept();

            try {
                messageWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(client.getInputStream()));

                Message connectionRequest = new Message(inputBuffer.readLine());
                String clientAddress = client.getRemoteSocketAddress().toString();

                if(messageReceiver.validateConnectionRequest(connectionRequest, clientAddress)) {
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
