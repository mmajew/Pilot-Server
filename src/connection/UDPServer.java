package connection;

import tools.ServerLogger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class UdpServer extends Thread {
    private DatagramSocket socket;
    private boolean isRunning = false;

    public void initialize(Integer port) throws SocketException {
        System.out.println("UDP server initialized");
        socket = new DatagramSocket(port);
    }

    @Override
    public void run()
    {
        System.out.println("UDP server starting");
        byte[] datagram = new byte[1024];
        isRunning = true;
        try {
            while (isRunning) {
                DatagramPacket receivePacket = new DatagramPacket(datagram, datagram.length);
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData());
                System.out.println("Received: " + message);
            }
        } catch(IOException exception) {
            System.out.println("Error receiving an UDP packet.");
        } finally {
            socket.close();
        }
    }

    public void close() {
        if(socket != null)
            socket.close();
        isRunning = false;
    }
}
