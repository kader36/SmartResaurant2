package Controllers.Tables;

import Models.ClientSocket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class OrdersServer {
    public static ServerSocket serverSocket;
    public static ArrayList<ClientSocket> ListClient= new ArrayList<>();
    public static boolean bool=false;



    public static void startListeningToOrders(){

        try {
            String port=readfile(new File(System.getProperty("user.dir") + "/BD/port.txt"));
             //Server.startListeningToOrders();
            serverSocket = new ServerSocket(Integer.parseInt(port));
            bool=true;
            System.out.println("Server is connected");
            try {
                while(true){
                    // wait for the clients (the tablet connection).
                    Socket socket = serverSocket.accept();
                    ClientSocket clientSocket = new ClientSocket(socket);
                    Thread thread=new Thread(clientSocket);
                    thread.start();
                        }
                } catch (Exception e) {

                System.err.println("Connection lost with client");
                e.printStackTrace();

            }finally{
                System.err.println("connection completed");
            }

        } catch (IOException e) {
            System.err.println("connection cant be established");
        }

    }
    private static String readfile(File fileReader){
        String txt="";
        try { Scanner scanner = new Scanner( fileReader);
            String text = scanner.useDelimiter(";").next();
            txt=text;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return txt;
    }
}

    // ======= to do ==============.
    // add the rating to database and to the information in the food model so i can send it to the tablet.
    // refresh the socket design to accept multi clients.
    // get the food real data after you get it from teh order (in the order you get only the id)
    // add the food rating.

    // ======= added things to database =======.
    /*
    * added the COLOR attribute to the food and drinks category ---- DONE.
    * added the money withdrawl table ----- DONE.
    * maybe will also add the table gained money per day table ---- DONE.
    * */

