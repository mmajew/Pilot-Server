package servers;

import main.MainFrame;
import messages.Message;
import messages.MessageReceiver;
import tools.ServerLogger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class TcpServer extends Thread {
    private String clientAddress;
    private PrintWriter messageWriter;
    private MainFrame serverFrame;
    private MessageReceiver messageReceiver;

    ServerSocket serverSocket;
    Socket clientSocket;

    private Settings serverSettings;
    private boolean isRunning = false;
    private boolean isConfirmed = false;

    public TcpServer(MainFrame frame, Settings settings) {
        serverFrame = frame;
        serverSettings = settings;
    }

    public void sendMessage(String message){
        if (messageWriter != null && !messageWriter.checkError()) {
            System.out.println("Sending: " + message);
            messageWriter.println(message);
            messageWriter.flush();
        }
    }

    public void runServerLoop(BufferedReader inputBuffer) throws IOException {
        while (isRunning) {
            String message = inputBuffer.readLine();
            if (message != null) {
                System.out.println("Received: " + message);
                messageReceiver.receiveMessage(new Message(message));
            }
        }
    }

    @Override
    public void run() {
        try {
            isRunning = true;

            serverFrame.enableStopButton();
            ServerLogger.logMessage("Oczekiwanie na połączenie");

            serverSocket = new ServerSocket(serverSettings.getPort());
            clientSocket = serverSocket.accept();
            clientSocket.setKeepAlive(true);
            clientAddress = clientSocket.getRemoteSocketAddress().toString().substring(1);

            messageWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
            BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            messageReceiver = new MessageReceiver(this);
            runServerLoop(inputBuffer);

        } catch (IOException exception) {
            if(isRunning)
                ServerLogger.logMessage("Utracono połaczenie");
            else
                ServerLogger.logMessage("Zatrzymano serwer");
        }finally {
            this.close();
        }
    }

    public void close() {
        isRunning = false;
        if(messageReceiver != null) {
            messageReceiver.close();
        }
        try {
            if(serverSocket != null)
                serverSocket.close();
            if(clientSocket != null)
                clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverFrame.enableStartButton();
    }

    public String getClientAddress() {
        return clientAddress != null ? clientAddress : "";
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

    public boolean isAutoConfimEnabled() {
        return serverSettings.getEnableAutoConfirm();
    }
}
