package com.todc.ddengine.ui;

import com.todc.ddengine.ui.terminal.Terminal;
import com.todc.ddengine.world.Map;
import com.todc.ddengine.world.MapBuilder;
import com.todc.ddengine.world.Terrain;
import com.todc.ddengine.world.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
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

public class Game extends JFrame {

    private static Terminal terminal;
    private static Map map;
    private static int playerX = 1;
    private static int playerY = 1;

    public static void main(String... args) throws Exception {
        //SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("my window title");
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            // center window in monitor
            window.setLocationRelativeTo(null);

            // we'll explicitly paint our UI as needed
            window.setIgnoreRepaint(true);

            map = MapBuilder.fromString(
                    "##########\n" +
                    "#........#\n" +
                    "#........#\n" +
                    "#........#\n" +
                    "#........#\n" +
                    "##########\n"
            );

            // create components and put them in the frame
            terminal = new Terminal(20, 10);

            for (int y=0; y<map.getTiles().length; y++) {
                Tile[] cols = map.getTiles()[y];

                for (int x=0; x<cols.length; x++) {
                    Tile tile = cols[x];
                    String glyph = getGlyph(tile.getTerrainType());

                    terminal.setCell(x, y, glyph, Color.WHITE, Color.BLACK);
                }
            }

            window.add(terminal);
            window.pack();
            window.setVisible(true);

            window.repaint();

            System.out.println("Waiting for key presses...");
            boolean quit = false;
            while (!quit) {
                if (!terminal.hasFocus()) {
                    terminal.requestFocusInWindow();
                }

                int key = terminal.getKeyPressed();

                switch (key) {
                    case KeyEvent.VK_Q:
                        quit = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        movePlayer(-1, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        movePlayer(1, 0);
                        break;
                    case KeyEvent.VK_UP:
                        movePlayer(0, -1);
                        break;
                    case KeyEvent.VK_DOWN:
                        movePlayer(0, 1);
                        break;
                }
            }
        //});
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    private static void movePlayer(int xDelta, int yDelta) {

    }

    private static String getGlyph(int terrainType) {
        switch (terrainType) {
            case Terrain.FLOOR:
                return ".";
            case Terrain.WALL:
                return "#";
            default:
                return "!";
        }
    }

}
