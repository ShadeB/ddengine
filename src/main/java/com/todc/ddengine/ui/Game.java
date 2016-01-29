package com.todc.ddengine.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Tim O'Donnell (tim.odonnell@imperva.com)
 */
/*
public class Game {

    public static void main(String... args) throws Exception {
        JFrame frame = new JFrame("my window title");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // create components and put them in the frame

        // the pack method sizes the frame so that all its contents are at or above their preferred sizes.
        // An alternative to pack is to establish a frame size explicitly by calling setSize or setBounds (which
        // also sets the frame location). In general, using pack is preferable to calling setSize, since pack
        // leaves the frame layout manager in charge of the frame size, and layout managers are good at adjusting to
        // platform dependencies and other factors that affect component size.
        frame.setBounds(50, 50, 400, 300);

        // add our custom text component
        Container container = frame.getContentPane();
        Terminal terminal = new Terminal();
        container.add(terminal);

        frame.setVisible(true);

//        ActionListener listener = event -> {
//            System.out.println("refreshing terminal");
//            terminal.refresh();
//        };
//        Timer displayTimer = new Timer(5000, listener);
//        displayTimer.start();
    }

}
*/

public class Game {

    public static void main(String... args) throws Exception {
        JFrame window = new JFrame("my window title");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setBounds(50, 50, 400, 300);
        window.setBackground(Color.BLACK);

        // we'll explicitly paint our UI as needed
        window.setIgnoreRepaint(true);

        // create components and put them in the frame
        Terminal terminal = new Terminal();
        terminal.setIgnoreRepaint(true);
        terminal.setSize(100, 100);

        window.add(terminal);
        window.pack();
        window.setVisible(true);

        window.repaint();
    }

}
