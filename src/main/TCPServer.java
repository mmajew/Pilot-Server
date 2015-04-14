package main;

import tools.IPAssistant;
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
    final MessageReceiver messageListener;


    public static void main(String[] args) {
        new MainFrame();
    }


    public boolean isRunning() {
        return isRunning;
    }


    public TCPServer() {
        this.messageListener = new MessageReceiver();

        try {
            ServerLogger.logMessage(IPAssistant.getLocalAddress());
            ServerLogger.logMessage(IPAssistant.getExternalAddress());
        } catch (IOException exception) {
            Logger.getLogger(TCPServer.class.getName())
                    .log(Level.SEVERE, "Wystąpił błąd I/O podczas pobierania adresacji IP.", exception);
        }
        catch (Exception exception) {
            Logger.getLogger(TCPServer.class.getName())
                .log(Level.SEVERE, "Wystąpił nieoczekiwany błąd.", exception);
        }
    }


    public void sendMessage(String message){
        if (messageWriter != null && !messageWriter.checkError()) {
            messageWriter.println(message);
            messageWriter.flush();
        }
    }


    @Override
    public void run() {
        isRunning = true;

        try {
            ServerLogger.logMessage("Oczekiwanie na połączenie.");

            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            Socket client = serverSocket.accept();

            ServerLogger.logMessage("Połaczono.");

            try {
                messageWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                while (isRunning) {
                    String message = in.readLine();
                    if (message != null) {
                        messageListener.receiveMessage(message);
                    }
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
