package Controllers.Tables;

import Models.ClientSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static ServerSocket serverSocket2;
    public static ArrayList<ClientSocket> ListClient= new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void startListeningToOrders() {

        try {
            serverSocket2 = new ServerSocket(5010);
            System.out.println("waiting for messages 5010");
            while(true) {
                Socket socket2 = serverSocket2.accept();
                ClientSocket clientSocket = new ClientSocket(socket2);
                ListClient.add(clientSocket);
                pool.execute(clientSocket);

            }

            }catch(IOException e) {
        e.printStackTrace();
    }
}
}
