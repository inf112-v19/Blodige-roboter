package no.uib.inf112.core.multiplayer;

import java.net.*;
import java.io.*;


public class Server {


    public Server(int port, int numThreads) {
        ServerSocket servSock;

        try {
            servSock = new ServerSocket(port);

        } catch(IOException e) {
            /* Crash the server if IO fails. Something bad has happened */
            throw new RuntimeException("Could not create ServerSocket ", e);
        }

        // Create a series of threads and start them.
        for (int i=0; i<numThreads; i++) {
            new Handler(servSock, i).start();
        }
    }

    /** A Thread subclass to handle one client conversation. */
    class Handler extends Thread {
        ServerSocket handlerServSock;
        int threadNumber;

        /** Construct a Handler. */
        Handler(ServerSocket s, int i) {
            handlerServSock = s;
            threadNumber = i;
            setName("Thread " + threadNumber);
        }

        public void run() {
            /* Wait for a connection. Synchronized on the ServerSocket
             * while calling its accept() method.
             */
            while (true) {
                try {
                    System.out.println(getName() + " waiting");

                    Socket clientSocket;
                    // Wait here for the next connection.
                    synchronized(handlerServSock) {
                        clientSocket = handlerServSock.accept();
                    }
                    System.out.println(getName() + " starting, IP=" + clientSocket.getInetAddress());
                    DataInputStream inFromClient = new DataInputStream(clientSocket.getInputStream());
                    PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);

                    String line;
                    while ((line = inFromClient.readUTF()) != null) {
                        if(line.equals("getName")){
                            outToClient.print(getName() + "\r\n");
                            outToClient.flush();
                        }else{
                            outToClient.print(line + "\r\n");
                            outToClient.flush();
                        }
                    }
                } catch (EOFException e){
                    System.out.println( getName() + " Disconnected");
                    try {
                        handlerServSock.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch(IOException ex) {
                    System.out.println(getName() + ": IO Error on socket " + ex);
                    return;
                }
            }
        }
    }
}
