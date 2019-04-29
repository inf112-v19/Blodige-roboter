package no.uib.inf112.core.multiplayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Client {

    Socket clientSocket;
    DataOutputStream outToServer;
    BufferedReader inFromServer;
    String clientName = "";

    public Client(String IP, int port) {
        try {
            clientSocket = new Socket(IP, port);
            System.out.println("Server at" + clientSocket.getLocalAddress().getHostAddress());
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            clientName = getClientNameFromServer();
        } catch (IOException e) {
            System.out.println("Error while creating client: " + e);
        }
    }

    public List<String> getConnectedPlayers() {
        String result = "";
        try {
            outToServer.writeUTF("getConnectedPlayers");
            result = inFromServer.readLine();
        } catch (IOException e) {
            System.out.println("IOExeption " + e);
        }
        return Arrays.asList(result.split(","));
    }


    public String writeToServer(String text) {
        try {
            outToServer.writeUTF(text);
            String result = inFromServer.readLine();
            System.out.println("FROM SERVER FOR " + clientName + ": ");
            return result;

        } catch (IOException e) {
            System.out.println("IOExeption " + e);
        }
        return "Error receiveing from server";
    }

    public String getClientNameFromServer() {
        String result = "Error receiveing from server";
        try {
            outToServer.writeUTF("getName");
            result = inFromServer.readLine();
        } catch (IOException e) {
            System.out.println("IOExeption " + e);
        }
        return result;
    }

    public void closeConnection() {
        try {
            clientSocket.close();
            inFromServer.close();
            outToServer.close();
        } catch (IOException e) {
            System.out.println("IOExeption " + e);
        }
    }


    public void setName(String name) {
        writeToServer("setDisplayName:" + name);
    }

    public void startGame() {
        writeToServer("startGame");
    }
}