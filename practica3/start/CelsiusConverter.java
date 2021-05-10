/**
 * CelsiusConverter.java is a 1.4 application that 
 * demonstrates the use of JButton, JTextField and
 * JLabel.  It requires no other files.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CelsiusConverter implements ActionListener {
    JFrame converterFrame;
    JPanel converterPanel;
    JTextField tempCelsius;
    JLabel celsiusLabel, fahrenheitLabel;
    JButton convertTemp;

    public CelsiusConverter() {
        //Create and set up the window.
        converterFrame = new JFrame("Celsius to Fahrenheit");
        
        converterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the panel.
        converterPanel = new JPanel();
        converterPanel.setLayout(new GridLayout(2, 2));
        converterPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        //Add the widgets.
        addWidgets();

        //Set the default button.
        converterFrame.getRootPane().setDefaultButton(convertTemp);

        //Add the panel to the window.
        converterFrame.getContentPane().add(converterPanel, BorderLayout.CENTER);
        converterPanel.setBackground(new Color(45,45,45));

        //Display the window.
        converterFrame.pack();
        converterFrame.setLocationRelativeTo(null);
        converterFrame.setVisible(true);
    }

    /**
     * Create and add the widgets.
     */
    private void addWidgets() {
        //Create widgets.
        tempCelsius = new JTextField();
        celsiusLabel = new JLabel("Celsius", SwingConstants.CENTER);
        fahrenheitLabel = new JLabel("Fahrenheit", SwingConstants.CENTER);
        convertTemp = new JButton("Convert");

        tempCelsius.setBackground(new Color(75,75,75));
        tempCelsius.setForeground(new Color(220,220,220));
        celsiusLabel.setForeground(new Color(220,220,220));
        fahrenheitLabel.setForeground(new Color(220,220,220));
        convertTemp.setBackground(new Color(75,75,75));
        convertTemp.setOpaque(true);
        convertTemp.setBorderPainted(false);
        convertTemp.setForeground(new Color(220,220,220));

        //Listen to events from the Convert button (JButton) and return key (JTextField).
        //tempCelsius.addActionListener(this);
        convertTemp.addActionListener(this);

        //Add the widgets to the container.
        converterPanel.add(tempCelsius);
        converterPanel.add(celsiusLabel);
        converterPanel.add(convertTemp);
        converterPanel.add(fahrenheitLabel);
        
        celsiusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        fahrenheitLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void actionPerformed(ActionEvent event) {
        //Parse degrees Celsius as a double and convert to Fahrenheit.
        int tempFahrenheit = (int) (Integer.parseInt(tempCelsius.getText()) * 1.8 + 32);
        celsiusLabel.setText(Integer.parseInt(tempCelsius.getText()) + " Celsius");
        fahrenheitLabel.setText(tempFahrenheit + " Fahrenheit");
        tempCelsius.setText("");
        converterFrame.pack();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Set the look and feel.
        try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        CelsiusConverter converter = new CelsiusConverter();
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
