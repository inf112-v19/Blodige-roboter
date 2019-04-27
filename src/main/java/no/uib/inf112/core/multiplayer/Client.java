package no.uib.inf112.core.multiplayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    Socket clientSocket;
    DataOutputStream outToServer;
    BufferedReader inFromServer;
    String clientName = "";

    public Client(String IP, int port) {
        try {
            clientSocket = new Socket(IP, port);

            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            clientName = getClientNameFromServer();
        } catch (IOException e){
            System.out.println("Error while creating client: " + e);
        }
    }


    public void writeToServer(String text) {
        try {
            outToServer.writeUTF(text);
            System.out.println("FROM SERVER FOR " + clientName +": "+ inFromServer.readLine());
        }catch (IOException e){
            System.out.println("IOExeption " + e);
        }
    }

    public String getClientNameFromServer() {
        String result = "";
        try {
            outToServer.writeUTF("getName");
            result = inFromServer.readLine();
        }catch (IOException e){
            System.out.println("IOExeption " + e);
        }
        return result;
    }

    public void closeConnection(){
        try {
            clientSocket.close();
        }catch (IOException e){
            System.out.println("IOExeption " + e);
        }
    }
}