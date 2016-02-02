package com.todc.ddengine.ui;

import com.todc.ddengine.action.ActionResult;
import com.todc.ddengine.util.Direction;
import com.todc.ddengine.action.Action;
import com.todc.ddengine.action.MoveAction;
import com.todc.ddengine.action.NoOpAction;
import com.todc.ddengine.ui.terminal.Terminal;
import com.todc.ddengine.world.Hero;
import com.todc.ddengine.world.Stage;
import com.todc.ddengine.world.Tile;
import com.todc.ddengine.world.dungeon.DungeonGenerator;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


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

public class Game {

    public static void main(String... args) throws Exception {
        DungeonGenerator generator = new DungeonGenerator();
        Tile[][] tiles = generator.generate(35, 35);
        Stage stage = new Stage(tiles);

        Hero hero = new Hero();
        hero.setPosition(stage.findEmptyTile());
        stage.setHero(hero);

        Terminal terminal = new Terminal(20, 10);

        Screen screen = new Screen(stage, terminal);
        screen.refresh();

        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("my window title");
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            // center window in monitor
            window.setLocationRelativeTo(null);

            // we'll explicitly paint our UI as needed
            window.setIgnoreRepaint(true);

            window.add(terminal);
            window.pack();
            window.setVisible(true);

            window.repaint();
        });

        boolean quit = false;
        while (!quit) {
            if (!terminal.hasFocus()) {
                terminal.requestFocusInWindow();
            }

            Integer key = terminal.getKeyPressed();
            if (key == null) continue;

            Action action = new NoOpAction();

            switch (key) {
                case KeyEvent.VK_Q:
                    quit = true;
                    break;
                case KeyEvent.VK_7:
                    action = new MoveAction(stage, stage.getHero(), Direction.NW);
                    break;
                case KeyEvent.VK_8:
                case KeyEvent.VK_UP:
                    action = new MoveAction(stage, stage.getHero(), Direction.N);
                    break;
                case KeyEvent.VK_9:
                    action = new MoveAction(stage, stage.getHero(), Direction.NE);
                    break;
                case KeyEvent.VK_4:
                case KeyEvent.VK_LEFT:
                    action = new MoveAction(stage, stage.getHero(), Direction.W);
                    break;
                case KeyEvent.VK_6:
                case KeyEvent.VK_RIGHT:
                    action = new MoveAction(stage, stage.getHero(), Direction.E);
                    break;
                case KeyEvent.VK_1:
                    action = new MoveAction(stage, stage.getHero(), Direction.SW);
                    break;
                case KeyEvent.VK_2:
                case KeyEvent.VK_DOWN:
                    action = new MoveAction(stage, stage.getHero(), Direction.S);
                    break;
                case KeyEvent.VK_3:
                    action = new MoveAction(stage, stage.getHero(), Direction.SE);
                    break;
            }

            ActionResult result = action.perform();
            while (result.hasAlternate()) {
                action = result.getAlternate();
                result = action.perform();
            }
        }
    }

}
