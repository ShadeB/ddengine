package com.todc.ddengine.ui;


import com.todc.ddengine.ui.terminal.Cell;
import com.todc.ddengine.ui.terminal.Terminal;
import com.todc.ddengine.util.Coordinate;
import com.todc.ddengine.world.Actor;
import com.todc.ddengine.world.Stage;
import com.todc.ddengine.world.StageListener;
import com.todc.ddengine.world.Terrain;
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

    private Rectangle visibleBounds;


    // ----------------------------------------------------------- Constructors


    public Screen(Stage stage, Terminal terminal) {
        this.stage = stage;
        this.terminal = terminal;

        // add map to stage
        for (int y = 0; y< stage.getTiles().length; y++) {
            Tile[] cols = stage.getTiles()[y];

            for (int x=0; x<cols.length; x++) {
                Tile tile = cols[x];
                String glyph = getGlyph(tile.getTerrainType());

                terminal.setCell(x, y, glyph, Color.WHITE, Color.BLACK);
            }
        }

        // add actors
        Coordinate heroPosition = stage.getHero().getPosition();
        terminal.setCell(heroPosition.x, heroPosition.y, "@", Color.YELLOW, Color.BLACK);

        this.stage.addChangeListener(this);
    }


    // --------------------------------------------------------- Public Methods

    @Override
    public void onStageChange(Actor actor, Coordinate oldPosition, Coordinate newPosition) {
        System.out.println("handling stage change");
        terminal.setCell(oldPosition.x, oldPosition.y, ".", Color.WHITE, Color.BLACK);
        terminal.setCell(newPosition.x, newPosition.y, "@", Color.YELLOW, Color.BLACK);
    }


    // -------------------------------------------------------- Private Methods


    private String getGlyph(int terrainType) {
        switch (terrainType) {
            case Terrain.FLOOR:
                return ".";
            case Terrain.WALL:
                return "#";
            default:
                return " ";
        }
    }
}
