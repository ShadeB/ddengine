package com.todc.ddengine.ui.terminal;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ArrayBlockingQueue;


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
        return keyEvents.take();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int id = e.getID();
        String keyString;

        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } else {
            int keyCode = e.getKeyCode();
            keyString = "key code = " + keyCode
                    + " ("
                    + KeyEvent.getKeyText(keyCode)
                    + ")";
        }

        int modifiersEx = e.getModifiersEx();
        String modString = "extended modifiers = " + modifiersEx;
        String tmpString = KeyEvent.getModifiersExText(modifiersEx);
        if (tmpString.length() > 0) {
            modString += " (" + tmpString + ")";
        } else {
            modString += " (no extended modifiers)";
        }

        String actionString = "action key? ";
        if (e.isActionKey()) {
            actionString += "YES";
        } else {
            actionString += "NO";
        }

        String locationString = "key location: ";
        int location = e.getKeyLocation();
        if (location == KeyEvent.KEY_LOCATION_STANDARD) {
            locationString += "standard";
        } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
            locationString += "left";
        } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
            locationString += "right";
        } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
            locationString += "numpad";
        } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
            locationString += "unknown";
        }

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
