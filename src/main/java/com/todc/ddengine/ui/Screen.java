package com.todc.ddengine.ui;


import com.todc.ddengine.ui.terminal.Terminal;
import com.todc.ddengine.util.Coordinate;
import com.todc.ddengine.world.Actor;
import com.todc.ddengine.world.Glyph;
import com.todc.ddengine.world.Stage;
import com.todc.ddengine.world.StageListener;
import com.todc.ddengine.world.Tile;

import java.awt.Rectangle;


/**
 * Mediates changes between the Stage and Terminal.
 *
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Screen implements StageListener {


    // ----------------------------------------------------- Instance Variables


    private Terminal terminal;
    private Stage stage;
    private Rectangle viewport;


    // ----------------------------------------------------------- Constructors


    public Screen(Stage stage, Terminal terminal) {
        this.stage = stage;
        this.terminal = terminal;
        this.viewport = new Rectangle(0, 0, terminal.getNumCols(), terminal.getNumRows());

        centerViewport();

        this.stage.addChangeListener(this);
    }


    // --------------------------------------------------------- Public Methods

    @Override
    public void onStageChange(Actor actor, Coordinate oldPosition, Coordinate newPosition) {
        // is hero off screen?
        if (isActorOffscreen(actor)) {
            System.out.println("Hero is offscreen.");
            centerViewport();
        }

        refresh();
    }

    public void refresh() {
        copyStageToTerminal();
        terminal.repaint();
    }


    // -------------------------------------------------------- Private Methods


    private boolean isActorOffscreen(Actor actor) {
        Coordinate position = actor.getPosition();
        return !viewport.contains(position.x+2, position.y) ||
               !viewport.contains(position.x-2, position.y) ||
               !viewport.contains(position.x, position.y+2) ||
               !viewport.contains(position.x, position.y-2);
    }

    private void centerViewport() {
        Coordinate heroPosition = stage.getHero().getPosition();

        // check if viewport X would be too far left of stage
        int viewportX = Math.max(0, heroPosition.x-(viewport.width/2));

        // check if viewport X would be too far right of stage
        if (viewportX+viewport.width > stage.getWidth()) {
            viewportX = stage.getWidth()-viewport.width;
        }

        // check if viewport Y would be too far top of stage
        int viewportY = Math.max(0, heroPosition.y-(viewport.height/2));

        // check if viewport Y would be too far bottom of stage
        if (viewportY+viewport.height > stage.getHeight()) {
            viewportY = stage.getHeight()-viewport.height;
        }

        viewport = new Rectangle(viewportX, viewportY, viewport.width, viewport.height);
        System.out.println("Recentered viewport to: " + viewport.x + ", " + viewport.x + ", " + viewport.width + ", " + viewport.height);
    }

    private void copyStageToTerminal() {
        System.out.println("Copying stage to terminal");

        // add environment tiles
        for (int ty=0, vy=viewport.y; ty<viewport.height; ty++, vy++) {
            Tile[] cols = stage.getTiles()[vy];

            for (int tx=0, vx=viewport.x; tx<viewport.width; tx++, vx++) {
                Tile tile = cols[vx];
                Glyph glyph = tile.getGlyph();

                terminal.setCell(tx, ty, glyph.getCharacter(), glyph.getForeground(), glyph.getBackground());
            }
        }

        // add actors
        Coordinate heroPosition = stage.getHero().getPosition();
        Glyph heroGlyph = stage.getHero().getGlyph();
        terminal.setCell(
                heroPosition.x-viewport.x,
                heroPosition.y-viewport.y,
                heroGlyph.getCharacter(),
                heroGlyph.getForeground(),
                heroGlyph.getBackground()
        );
    }

}
