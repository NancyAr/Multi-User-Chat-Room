/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiuserchatroom;

/**
 *
 * @author NancyAlarabawy
 */
import java.io.*;
import java.net.*;
import java.util.*;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

// For every client's connection we call this class
    private String clientName;

    private Socket socket;
    private final Client[] threads;
    private int maxClientsCount;

    //private DataInputStream inStream;
    //private PrintStream outStream;
    // Scanner inStream = new Scanner(System.in);
    private BufferedReader inStream;
    private PrintWriter outStream;

    public Client(Socket socket, Client[] threads) {
        this.socket = socket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        Client[] threads = this.threads;

        try {
            /*
             * Create input and output streams for this client.
             */
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outStream = new PrintWriter(socket.getOutputStream(), true);
            String username;
            //We use @ token to send private messages, so we loop for input name until a name is provided free of '@' char
            while (true) {
                outStream.println("**Please Enter Username:");
                username = inStream.readLine().trim();
                if (username.indexOf('@') == -1) {
                    break;
                } else {
                    outStream.println("**Username should not contain '@' character. It is a used token! ");
                }
            }

            /* Welcome the new the client. */
            outStream.println("**Welcome " + username
                    + " to our chat room.\n**To leave enter /leave in a new line.");
            //Adding username to the client's name
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] == this) {
                        clientName = "@" + username;
                        break;
                    }
                }
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] != this) {
                        threads[i].outStream.println("**" + username
                                + " has entered the chat**");
                    }
                }
            }
            /* Start the conversation. */
            while (true) {
                String line = inStream.readLine();
                if (line.startsWith("/leave")) {
                    break;
                }
                /* If the message is private sent it to the given client. */
                if (line.startsWith("@")) {
                    String[] words = line.split("\\s", 2);
                    if (words.length > 1 && words[1] != null) {
                        words[1] = words[1].trim();
                        if (!words[1].isEmpty()) {
                            synchronized (this) {
                                for (int i = 0; i < maxClientsCount; i++) {
                                    if (threads[i] != null && threads[i] != this
                                            && threads[i].clientName != null
                                            && threads[i].clientName.equals(words[0])) {
                                        threads[i].outStream.println("<" + username + "> @" + threads[i].clientName + " " + words[1]);
                                        //Echoing msg to user to confirm it was sent privately
                                        this.outStream.println(">" + username + "> " + threads[i].clientName + " " + words[1]);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    /* The message is public, broadcast it to all other clients. */
                    synchronized (this) {
                        for (int i = 0; i < maxClientsCount; i++) {
                            if (threads[i] != null && threads[i].clientName != null) {
                                threads[i].outStream.println("<" + username + "> " + line);
                            }
                        }
                    }
                }
            }
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] != this
                            && threads[i].clientName != null) {
                        threads[i].outStream.println("** " + username
                                + " has left the chat **");
                    }
                }
            }
            outStream.println("** GoodBye " + username + " **");

            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == this) {
                        threads[i] = null;
                        maxClientsCount--;
                    }
                }
            }
            //closing
            inStream.close();
            outStream.close();
            socket.close();
        } catch (IOException e) {
        }
    }
}
