import java.awt.*;
import javax.swing.*;

/**
 * general purpose powerful free layouts:
 * JGoodies' FormLayout
 * MigLayout
 * DesignGridLayout
 */

public class Xat {
    private static void createAndShowGUI() {
        //Set the look and feel.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create and set up the window.
        JFrame frame = new JFrame("Xat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an output JPanel and add a JTextArea(20, 30) inside a JScrollPane
        JPanel outPanel = new JPanel();
        outPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        outPanel.setLayout(new BoxLayout(outPanel, BoxLayout.PAGE_AXIS));
        JTextArea messages = new JTextArea(20, 30);
        messages.setEditable(false);
        outPanel.add(new JScrollPane(messages));
        
        // Create an input JPanel and add a JTextField(25) and a JButton
        JPanel inPanel = new JPanel();
        inPanel.setLayout(new BoxLayout(inPanel, BoxLayout.LINE_AXIS));
        JTextField text = new JTextField(25);
        JButton btn = new JButton("Send");
        inPanel.add(text);
        inPanel.add(btn);
        
        // add panels to main frame
        frame.add(outPanel, BorderLayout.CENTER);
        frame.add(inPanel, BorderLayout.PAGE_END);
        
        //Display the window centered.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
