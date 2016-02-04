package com.todc.ddengine.ui;


import com.todc.ddengine.data.Tiles;
import com.todc.ddengine.ui.terminal.Terminal;
import com.todc.ddengine.util.Coordinate;
import com.todc.ddengine.world.Actor;
import com.todc.ddengine.world.Glyph;
import com.todc.ddengine.world.Stage;
import com.todc.ddengine.world.StageListener;
import com.todc.ddengine.world.Tile;
import com.todc.ddengine.world.fov.AdamMilazzo;
import com.todc.ddengine.world.fov.FieldOfViewCalculator;

import java.awt.Rectangle;


/**
 * Mediates changes between the Stage and Terminal.
 *
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Screen implements StageListener {


    // ----------------------------------------------------- Instance Variables


    // todo: this should be a function of the hero
    private int VIEWABLE_RANGE = 5;

    private Terminal terminal;
    private Stage stage;
    private Rectangle viewport;
    private FieldOfViewCalculator fov;


    // ----------------------------------------------------------- Constructors


    public Screen(Stage stage, Terminal terminal) {
        this.stage = stage;
        this.terminal = terminal;
        this.viewport = new Rectangle(0, 0, terminal.getNumCols(), terminal.getNumRows());
        this.fov = new AdamMilazzo(this::blocksLight, this::setVisible, this::getDistance);

        centerViewport();

        this.stage.addChangeListener(this);
    }


    // --------------------------------------------------------- Public Methods


    @Override
    public void onStageChange(Actor actor) {
        // is hero off screen?
        if (isOffscreen(stage.getHero().getPosition())) {
            centerViewport();
        }

        refresh();
    }

    public void refresh() {
        copyStageToTerminal();

        Coordinate pos = stage.getHero().getPosition();
        fov.compute(pos.x, pos.y, VIEWABLE_RANGE);

        terminal.repaint();
    }


    // -------------------------------------------------------- Private Methods


    // FieldOfViewCalculator callback
    private boolean blocksLight(Integer x, Integer y) {
        return this.stage.getTileAt(x, y) == Tiles.WALL_TILE;
    }

    // FieldOfViewCalculator callback
    private void setVisible(Integer x, Integer y) {

    }

    // FieldOfViewCalculator callback
    private Integer getDistance(Integer x, Integer y) {
        return Math.abs(x) + Math.abs(y);
    }


    private boolean isOffscreen(Coordinate position) {
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
            for (int tx=0, vx=viewport.x; tx<viewport.width; tx++, vx++) {
                Tile tile = stage.getTileAt(vx, vy);
                assert(tile != null);
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
