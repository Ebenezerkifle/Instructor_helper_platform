package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerNextToMain  extends Thread {

    private final int port;
    private ArrayList<ServerWorker> workers=new ArrayList<>();

    public ServerNextToMain(int port) {
        this.port=port;
    }

    public ArrayList<ServerWorker> getWorkers() {
        return workers;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket=new ServerSocket(port);
            System.out.println("waiting for clients...");
            while(true){
                Socket socket=serverSocket.accept();
                System.out.println("connected");
                ServerWorker serverWorker=new ServerWorker(this ,socket);
                workers.add(serverWorker);
                serverWorker.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
