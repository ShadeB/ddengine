package com.todc.ddengine.ui.terminal;


import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class TerminalKeyListener implements KeyListener {


    // -------------------------------------------------------------- Constants


    private static final int NUMBER_OF_KEY_EVENTS = 1024;


    // ----------------------------------------------------- Instance Variables


    private ArrayBlockingQueue<Integer> keyEvents = new ArrayBlockingQueue<>(NUMBER_OF_KEY_EVENTS);


    // --------------------------------------------------------- Public Methods


    public Integer blockingKeyPressed() throws InterruptedException {
        return keyEvents.poll(10, TimeUnit.MILLISECONDS);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getExtendedKeyCode();
        System.out.println("Key: " + key + " (" + (char)key + ")");
        keyEvents.add(key);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // do nothing
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // do nothing
    }


}
