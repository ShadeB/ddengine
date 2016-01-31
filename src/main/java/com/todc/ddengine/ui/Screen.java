package com.todc.ddengine.ui;


import com.todc.ddengine.ui.terminal.Terminal;
import com.todc.ddengine.util.Coordinate;
import com.todc.ddengine.world.Actor;
import com.todc.ddengine.world.Glyph;
import com.todc.ddengine.world.Stage;
import com.todc.ddengine.world.StageListener;
import com.todc.ddengine.world.Tile;

import java.awt.Color;
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

        // add map to terminal
        for (int y = 0; y< stage.getTiles().length; y++) {
            Tile[] cols = stage.getTiles()[y];

            for (int x=0; x<cols.length; x++) {
                Tile tile = cols[x];
                Glyph glyph = tile.getGlyph();

                terminal.setCell(x, y, glyph.getCharacter(), glyph.getForeground(), glyph.getBackground());
            }
        }

        // add actors
        Glyph heroGlyph = stage.getHero().getGlyph();
        Coordinate heroPosition = stage.getHero().getPosition();
        terminal.setCell(
            heroPosition.x,
            heroPosition.y,
            heroGlyph.getCharacter(),
            heroGlyph.getForeground(),
            heroGlyph.getBackground()
        );

        this.stage.addChangeListener(this);
    }


    // --------------------------------------------------------- Public Methods

    @Override
    public void onStageChange(Actor actor, Coordinate oldPosition, Coordinate newPosition) {
        Glyph actorGlyph = actor.getGlyph();
        Tile oldTile = stage.getTileAt(oldPosition.x, oldPosition.y);
        Glyph oldGlyph = oldTile.getGlyph();

        // reset previous cell's tile
        terminal.setCell(
            oldPosition.x,
            oldPosition.y,
            oldGlyph.getCharacter(),
            oldGlyph.getForeground(),
            oldGlyph.getBackground()
        );

        // draw actor in new location
        terminal.setCell(
            newPosition.x,
            newPosition.y,
            actorGlyph.getCharacter(),
            actorGlyph.getForeground(),
            actorGlyph.getBackground()
        );
    }


    // -------------------------------------------------------- Private Methods

}
