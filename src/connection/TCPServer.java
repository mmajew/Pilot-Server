package connection;

import main.MainFrame;
import messages.Message;
import messages.MessageReceiver;
import tools.ServerLogger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class TcpServer extends Thread {
    final public static int SERVER_PORT = 4444;

    private String clientAddress;
    private PrintWriter messageWriter;
    private MainFrame serverFrame;
    private MessageReceiver messageReceiver;

    ServerSocket serverSocket;
    Socket clientSocket;

    private boolean isRunning = false;
    private boolean isConfirmed = false;

    public TcpServer(MainFrame frame) {
        serverFrame = frame;
    }

    public void sendMessage(String message){
        if (messageWriter != null && !messageWriter.checkError()) {
            System.out.println("Sending: " + message);
            messageWriter.println(message);
            messageWriter.flush();
        }
    }

    public String getClientAddress() {
        return clientAddress != null ? clientAddress : "";
    }

    @Override
    public void run() {
        try {
            System.out.println("Starting");
            ServerLogger.logMessage("Oczekiwanie na połączenie.");
            serverSocket = new ServerSocket(SERVER_PORT);
            clientSocket = serverSocket.accept();
            clientSocket.setKeepAlive(true);

            try {
                System.out.println("Initialized");
                messageReceiver = new MessageReceiver(this);
                messageWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
                BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                clientAddress = clientSocket.getRemoteSocketAddress().toString().substring(1);
                isRunning = true;
                serverFrame.enableStopButton();

                while (isRunning) {
                    String message = inputBuffer.readLine();
                    if (message != null) {
                        System.out.println("Received: " + message);
                        messageReceiver.receiveMessage(new Message(message));
                    }
                }
            } finally {
                this.close();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void close() {
        if(messageReceiver != null) {
            messageReceiver.close();
        }
        isRunning = false;
        try {
            serverSocket.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverFrame.enableStartButton();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void confirmConnection() {
        isConfirmed = true;
    }

    public boolean isConnectionConfirmed() {
        return isConfirmed;
    }
}
