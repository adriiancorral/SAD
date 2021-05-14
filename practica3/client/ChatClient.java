import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * general purpose powerful free layouts:
 * JGoodies' FormLayout
 * MigLayout
 * DesignGridLayout
 */

public class ChatClient implements ActionListener {

    private static JTextField text;
    private static JTextArea messages;
    private static DefaultListModel<String> listModel;

    public ChatClient() {
        // Create and set up the window.
        JFrame frame = new JFrame("Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an output JPanel and add a JTextArea(20, 30) inside a JScrollPane
        JPanel outPanel = new JPanel();
        outPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        outPanel.setLayout(new BoxLayout(outPanel, BoxLayout.PAGE_AXIS));
        messages = new JTextArea(20, 30);
        messages.setEditable(false);
        outPanel.add(new JScrollPane(messages));
        
        // Create an input JPanel and add a JTextField(25) and a JButton
        JPanel inPanel = new JPanel();
        inPanel.setLayout(new BoxLayout(inPanel, BoxLayout.LINE_AXIS));
        text = new JTextField(25);
        JButton btn = new JButton("Send");
        text.addActionListener(this);
        btn.addActionListener(this);
        inPanel.add(text);
        inPanel.add(btn);

        // Create a JScrollPanel and a JList
        listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(list);
        
        // add panels to main frame
        frame.add(outPanel, BorderLayout.CENTER);
        frame.add(inPanel, BorderLayout.PAGE_END);
        frame.add(listScrollPane, BorderLayout.EAST);
        
        //Display the window centered.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        EchoClient.writen.put("key", text.getText());
        messages.append(text.getText() + "\n");
        text.setText(null);
    }

    private static void createAndShowGUI() {
        //Set the look and feel.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        ChatClient chat = new ChatClient();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (args.length < 2) return;
        EchoClient client = new EchoClient(messages, listModel);
        client.mainClient(args[0], Integer.parseInt(args[1]), args[2]);
    }
}
