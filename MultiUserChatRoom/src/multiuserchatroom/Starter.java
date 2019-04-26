package multiuserchatroom;

import javax.swing.*;

public class Starter {

    static String username = "";

    public static void main(String[] args) {

        Object[] selectioValues = {"Server", "Client"};
        String initialSection = "Server";
        Object selection = JOptionPane.showInputDialog(null, "Login as : ", "MyChatApp", JOptionPane.QUESTION_MESSAGE, null, selectioValues, initialSection);
        if (selection.equals("Server")) {
            String[] arguments = new String[]{};
            new Server().main(arguments);
        } else if (selection.equals("Client")) {
            String IPServer = "localhost";
            String[] arguments = new String[]{IPServer};
            new ChatRoom().main(arguments);
        }

    }

}
