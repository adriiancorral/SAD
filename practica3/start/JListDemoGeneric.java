/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/* ListDemo.java requires no other files. */
public class JListDemoGeneric extends JPanel implements ActionListener {
    
    // define a JList and a DefaultListModel
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private JTextField entry;
    private JButton addButton;
    private JButton remButton;

    public JListDemoGeneric() {
        super(new BorderLayout());

        // create initial listModel
        listModel = new DefaultListModel<>();
        listModel.addElement("Pedro del Campillo");
        listModel.addElement("Ana Torroja");
        listModel.addElement("Manuel Tordesillas");
        listModel.addElement("Antonio Mesias");
        listModel.addElement("Jacinta Bermudez");

        //Create the list and put it in a scroll pane.
        list = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(list);

        add(listScrollPane, BorderLayout.CENTER);

        //Create the box and put it in the JPanel
        addWidgets();
    }

    /**
     * Create and add the widgets.
     */
    private void addWidgets() {
        JPanel inp = new JPanel();
        inp.setLayout(new BoxLayout(inp, BoxLayout.LINE_AXIS));
        //Create widgets.
        entry = new JTextField();
        addButton = new JButton("Add");
        remButton = new JButton("Remove");

        //Listen to events from the Convert button (JButton) and return key (JTextField).
        entry.addActionListener(this);
        addButton.addActionListener(this);
        remButton.addActionListener(this);

        //Add the widgets to the container.
        inp.add(entry);
        inp.add(addButton);
        inp.add(remButton);

        //Add JPanel to JFrame
        add(inp, BorderLayout.PAGE_END);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if(source == remButton) {
            listModel.removeElement(entry.getText());
        }
        if ((source == addButton || source == entry) && (!listModel.contains(entry.getText()))) {
            listModel.addElement(entry.getText());
        }
        entry.setText(null);
        if (source == listModel) {
            entry.setText("Funciona");
        }
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
        
        //Create and set up the window.
        JFrame frame = new JFrame("List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        frame.setContentPane(new JListDemoGeneric());

        //Display the window.
        frame.pack();
        frame.setSize(400, frame.getHeight());
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
