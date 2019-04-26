package multiuserchatroom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.event.*;
import javax.swing.text.*;

public class ChatRoom {

    static class ChatAccess extends Observable {

        private Socket socket;
        private OutputStream outputStream;

        @Override
        public void notifyObservers(Object arg) {
            super.setChanged();
            super.notifyObservers(arg);
        }

        public void InitSocket(String server, int port) throws IOException {
            socket = new Socket(server, port);
            outputStream = socket.getOutputStream();

            Thread receivingThread = new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            notifyObservers(line);
                        }
                    } catch (IOException ex) {
                        notifyObservers(ex);
                    }
                }
            };
            receivingThread.start();
        }

        public void send(String text) {
            try {
                outputStream.write((text + "\r\n").getBytes());
                outputStream.flush();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }

        public void close() {
            try {
                socket.close();
            } catch (IOException ex) {
                notifyObservers(ex);
            }
        }
    }

    /**
     * Chat client UI
     */
    static class ChatFrame extends JFrame implements Observer {

        // private JEditorPane EditPane;
        private JTextPane EditPane;
        private JTextPane textPane;
        private JTextField inputTextField;
        private JButton sendButton;
        private ChatAccess chatAccess;

        public ChatFrame(ChatAccess chatAccess) {
            this.chatAccess = chatAccess;
            chatAccess.addObserver(this);
            buildGUI();
        }

        /**
         * Builds the user interface
         */
        private void buildGUI() {
            /*textArea = new JTextArea(20, 50);
             textArea.setEditable(false);
             textArea.setLineWrap(true);
             add(new JScrollPane(textArea), BorderLayout.CENTER);*/

            textPane = new JTextPane();
            JScrollPane editorScrollPane = new JScrollPane(textPane);
            editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            editorScrollPane.setPreferredSize(new Dimension(500, 500));
            textPane.setEditable(false);
            add(editorScrollPane, BorderLayout.CENTER);

            Box box1 = Box.createHorizontalBox();
            add(box1, BorderLayout.NORTH);
            EditPane = new JTextPane();
            EditPane.setEditable(false);
            JScrollPane editorScrollPane2 = new JScrollPane(EditPane);
            editorScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            EditPane.setLayout(new BorderLayout());
            EditPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Emojis"));
            box1.add(editorScrollPane2);
            EditPane.setSize(300, 100);

            Document doc = EditPane.getDocument();
            SimpleAttributeSet space = new SimpleAttributeSet();
            SimpleAttributeSet attrs1 = new SimpleAttributeSet();
            SimpleAttributeSet attrs2 = new SimpleAttributeSet();
            SimpleAttributeSet attrs3 = new SimpleAttributeSet();
            SimpleAttributeSet attrs4 = new SimpleAttributeSet();
            SimpleAttributeSet attrs5 = new SimpleAttributeSet();
            SimpleAttributeSet attrs6 = new SimpleAttributeSet();
            SimpleAttributeSet attrs7 = new SimpleAttributeSet();
            SimpleAttributeSet attrs8 = new SimpleAttributeSet();
            SimpleAttributeSet attrs9 = new SimpleAttributeSet();
            SimpleAttributeSet attrs10 = new SimpleAttributeSet();

            //getting emojis and resizing them
            StyleConstants.setIcon(attrs1, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download.jpg"));
            StyleConstants.setIcon(attrs2, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download2.jpg"));
            StyleConstants.setIcon(attrs3, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download4.jpg"));
            StyleConstants.setIcon(attrs4, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download5.jpg"));
            StyleConstants.setIcon(attrs5, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download6.jpg"));
            StyleConstants.setIcon(attrs6, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download7.jpg"));
            StyleConstants.setIcon(attrs7, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download8.jpg"));
            StyleConstants.setIcon(attrs8, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download9.jpg"));
            StyleConstants.setIcon(attrs9, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download10.jpg"));
            StyleConstants.setIcon(attrs10, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download11.jpg"));

            StyleConstants.setForeground(space, Color.WHITE);
            try {
                doc.insertString(doc.getLength(), " :) ", space);
                doc.insertString(doc.getLength(), " ", attrs1);
                doc.insertString(doc.getLength(), " :P ", space);
                doc.insertString(doc.getLength(), "  ", attrs2);
                doc.insertString(doc.getLength(), " <3 ", space);
                doc.insertString(doc.getLength(), "  ", attrs3);
                doc.insertString(doc.getLength(), " </3  ", space);
                doc.insertString(doc.getLength(), "  ", attrs4);
                doc.insertString(doc.getLength(), " ;) ", space);
                doc.insertString(doc.getLength(), " ", attrs5);
                doc.insertString(doc.getLength(), " :'D ", space);
                doc.insertString(doc.getLength(), " ", attrs6);
                doc.insertString(doc.getLength(), " :'D ", space);
                doc.insertString(doc.getLength(), " ", attrs7);
                doc.insertString(doc.getLength(), " :'D ", space);
                doc.insertString(doc.getLength(), " ", attrs8);
                doc.insertString(doc.getLength(), " :'D ", space);
                doc.insertString(doc.getLength(), " ", attrs9);
                doc.insertString(doc.getLength(), " :'D ", space);
                doc.insertString(doc.getLength(), " ", attrs10);

            } catch (Exception e) {
                System.out.println(e);
            }
            EditPane.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    try {
                        StyledDocument doc1 = textPane.getStyledDocument();

                        if (e.getX() >= 16 && e.getX() <= 38) {
                            try {
                                String str = inputTextField.getText();
                                inputTextField.setText(str + " :) ");
                                // doc1.insertString(doc1.getLength(), " ", attrs1);
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                        }
                        if (e.getX() >= 54 && e.getX() <= 74) {
                            try {
                                String str = inputTextField.getText();
                                inputTextField.setText(str + " :P ");
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                        }
                        if (e.getX() >= 94 && e.getX() <= 115) {
                            try {
                                String str = inputTextField.getText();
                                inputTextField.setText(str + " <3 ");
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                        }
                        if (e.getX() >= 141 && e.getX() <= 160) {
                            try {
                                String str = inputTextField.getText();
                                inputTextField.setText(str + " </3 ");
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                        }
                        if (e.getX() >= 173 && e.getX() <= 193) {
                            try {
                                String str = inputTextField.getText();
                                inputTextField.setText(str + " ;) ");
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                        }
                        if (e.getX() >= 216 && e.getX() <= 233) {
                            try {
                                String str = inputTextField.getText();
                                inputTextField.setText(str + " :'D ");
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                        }
                        if (e.getX() >= 255 && e.getX() <= 274) {
                            try {
                                String str = inputTextField.getText();
                                inputTextField.setText(str + " :( ");
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                        }
                        if (e.getX() >= 293 && e.getX() <= 315) {
                            try {
                                String str = inputTextField.getText();
                                inputTextField.setText(str + " :'( ");
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                        }
                        if (e.getX() >= 332 && e.getX() <= 354) {
                            try {
                                String str = inputTextField.getText();
                                inputTextField.setText(str + " :') ");
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                        }
                        if (e.getX() >= 373 && e.getX() <= 393) {
                            try {
                                String str = inputTextField.getText();
                                inputTextField.setText(str + " :n&c ");
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }

                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            Box box = Box.createHorizontalBox();
            add(box, BorderLayout.SOUTH);
            inputTextField = new JTextField();
            sendButton = new JButton("Send");
            box.add(inputTextField);
            box.add(sendButton);

            // Action for the inputTextField and the goButton
            ActionListener sendListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String str = inputTextField.getText();
                    if (str != null && str.trim().length() > 0) {
                        chatAccess.send(str);
                    }
                    inputTextField.selectAll();
                    inputTextField.requestFocus();
                    inputTextField.setText("");
                }
            };
            inputTextField.addActionListener(sendListener);
            sendButton.addActionListener(sendListener);

            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    chatAccess.close();
                }
            });
        }

        /**
         * Updates the UI depending on the Object argument
         */
        public ImageIcon Resizer(String path) {
            ImageIcon imageIcon = new ImageIcon(path); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it 
            Image newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            imageIcon = new ImageIcon(newimg);  // transform it back
            return imageIcon;
        }

        String username[] = new String[20];
        int idx = 0;
        int r = 0;
        public static final Color PURPLE = new Color(102, 0, 153);

        public void update(Observable o, Object arg) {
            final Object finalArg = arg;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    StyledDocument doc = textPane.getStyledDocument();
                    String[] words = finalArg.toString().split("\\s");
                    words[0] = words[0].trim();
                    Boolean exists = false;

                    //if it is not a notification from the system
                    if (!words[0].startsWith("*")) {

                        SimpleAttributeSet keyWord = new SimpleAttributeSet();
                        SimpleAttributeSet attrs1 = new SimpleAttributeSet();
                        SimpleAttributeSet attrs2 = new SimpleAttributeSet();
                        SimpleAttributeSet attrs3 = new SimpleAttributeSet();
                        SimpleAttributeSet attrs4 = new SimpleAttributeSet();
                        SimpleAttributeSet attrs5 = new SimpleAttributeSet();
                        SimpleAttributeSet attrs6 = new SimpleAttributeSet();
                        SimpleAttributeSet attrs7 = new SimpleAttributeSet();
                        SimpleAttributeSet attrs8 = new SimpleAttributeSet();
                        SimpleAttributeSet attrs9 = new SimpleAttributeSet();
                        SimpleAttributeSet attrs10 = new SimpleAttributeSet();

                        //styling username display
                        StyleConstants.setBold(keyWord, true);

                        if (r == 0) {
                            username[idx] = words[0];
                            StyleConstants.setForeground(keyWord, Color.RED);
                        } else {
                            for (int n = 0; n <= idx; n++) {
                                System.out.println(n);
                                if (username[n].equals(words[0])) {
                                    exists = true;
                                    if (n == 0) {
                                        StyleConstants.setForeground(keyWord, Color.RED);
                                    }
                                    if (n == 1) {
                                        StyleConstants.setForeground(keyWord, Color.BLUE);
                                    }
                                    if (n == 2) {
                                        StyleConstants.setForeground(keyWord, Color.CYAN);
                                    }
                                    if (n == 3) {
                                        StyleConstants.setForeground(keyWord, Color.magenta);
                                    }
                                    if (n == 4) {
                                        StyleConstants.setForeground(keyWord, Color.PINK);
                                    }
                                    if (n == 5) {
                                        StyleConstants.setForeground(keyWord, Color.ORANGE);
                                    }
                                    if (n == 6) {
                                        StyleConstants.setForeground(keyWord, Color.darkGray);
                                    }
                                    if (n == 7) {
                                        StyleConstants.setForeground(keyWord, Color.YELLOW);
                                    }
                                    if (n == 8) {
                                        StyleConstants.setForeground(keyWord, Color.green);
                                    }
                                    if (n == 9) {
                                        StyleConstants.setForeground(keyWord, Color.LIGHT_GRAY);
                                    }
                                }
                            }
                            if (!exists) {
                                idx++;
                                username[idx] = words[0];

                            }

                        }
                        int i = 1;
                        //System.out.println(username[idx]);
                        //getting emojis and resizing them
                        StyleConstants.setIcon(attrs1, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download.jpg"));
                        StyleConstants.setIcon(attrs2, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download2.jpg"));
                        StyleConstants.setIcon(attrs3, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download4.jpg"));
                        StyleConstants.setIcon(attrs4, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download5.jpg"));
                        StyleConstants.setIcon(attrs5, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download6.jpg"));
                        StyleConstants.setIcon(attrs6, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download7.jpg"));
                        StyleConstants.setIcon(attrs7, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download8.jpg"));
                        StyleConstants.setIcon(attrs8, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download9.jpg"));
                        StyleConstants.setIcon(attrs9, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download10.jpg"));
                        StyleConstants.setIcon(attrs10, Resizer("C:/Users/NancyAlarabawy/Documents/NetBeansProjects/MultiUserChatRoom/src/download11.jpg"));

                        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
                        String text = words[0] + " [" + timestamp + "]:";
                        //Color code for private messages is purple
                        if (finalArg.toString().indexOf("@") != -1) {
                            StyleConstants.setForeground(keyWord, PURPLE);
                            i = 2;
                        }
                        try {

                            doc.insertString(doc.getLength(), "\n" + text, keyWord);

                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        for (i = 1; i < words.length; i++) {
                            try {
                                if (words[i].equals(":)")) {
                                    doc.insertString(doc.getLength(), " ", attrs1);
                                    i++;
                                    if (i == words.length) {
                                        break;
                                    }
                                } else if (words[i].equals(":P")) {
                                    doc.insertString(doc.getLength(), " ", attrs2);
                                    i++;
                                    if (i == words.length) {
                                        break;
                                    }
                                } else if (words[i].equals("<3")) {
                                    doc.insertString(doc.getLength(), " ", attrs3);
                                    i++;
                                    if (i == words.length) {
                                        break;
                                    }
                                } else if (words[i].equals("</3")) {
                                    doc.insertString(doc.getLength(), " ", attrs4);
                                    i++;
                                    if (i == words.length) {
                                        break;
                                    }
                                } else if (words[i].equals(";)")) {
                                    doc.insertString(doc.getLength(), " ", attrs5);
                                    i++;
                                    if (i == words.length) {
                                        break;
                                    }
                                } else if (words[i].equals(":'D")) {
                                    doc.insertString(doc.getLength(), " ", attrs6);
                                    i++;
                                    if (i == words.length) {
                                        break;
                                    }
                                } else if (words[i].equals(":(")) {
                                    doc.insertString(doc.getLength(), " ", attrs7);
                                    i++;
                                    if (i == words.length) {
                                        break;
                                    }
                                } else if (words[i].equals(":'(")) {
                                    doc.insertString(doc.getLength(), " ", attrs8);
                                    i++;
                                    if (i == words.length) {
                                        break;
                                    }
                                } else if (words[i].equals(":')")) {
                                    doc.insertString(doc.getLength(), " ", attrs9);
                                    i++;
                                    if (i == words.length) {
                                        break;
                                    }
                                } else if (words[i].equals(":n&c")) {
                                    doc.insertString(doc.getLength(), " ", attrs10);
                                    i++;
                                    if (i == words.length) {
                                        break;
                                    }
                                }
                                if (finalArg.toString().indexOf("@") != -1) {
                                    doc.insertString(doc.getLength(), " " + words[i] + " ", keyWord);
                                } else {
                                    doc.insertString(doc.getLength(), " " + words[i] + " ", null);
                                    r++;
                                }

                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }

                    } else {
                        try {

                            doc.insertString(doc.getLength(), "\n" + finalArg.toString() + "\n", null);

                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }

            });
        }
    }

    public static void main(String[] args) {
        String server = args[0];
        int port = 2222;
        ChatAccess access = new ChatAccess();

        JFrame frame = new ChatFrame(access);
        frame.setTitle("MyChatApp - connected to " + server + ":" + port);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        try {
            access.InitSocket(server, port);
        } catch (IOException ex) {
            System.out.println("Cannot connect to " + server + ":" + port);
            ex.printStackTrace();
            System.exit(0);
        }
    }
}

