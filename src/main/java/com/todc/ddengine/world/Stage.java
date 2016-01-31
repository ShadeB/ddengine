package com.todc.ddengine.world;


import com.todc.ddengine.util.Coordinate;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Stage {


    // ----------------------------------------------------- Instance Variables


    private Tile[][] tiles;
    private Actor hero;

    private int width = 0;
    private int height = 0;

    private List<StageListener> changeListeners = new ArrayList<>();


    // --------------------------------------------------------------- Mutators


    public Tile[][] getTiles() {
        return tiles;
    }

    public Actor getHero() {
        return hero;
    }

    public void setHero(Actor hero) {
        this.hero = hero;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


    // ----------------------------------------------------------- Constructors


    public Stage(Tile[][] tiles) {
        this.tiles = tiles;
        this.height = tiles.length;

        // find the max width of the stage
        for (Tile[] row : this.tiles) {
            if (row.length > this.width) {
                this.width = row.length;
            }
        }
    }


    // --------------------------------------------------------- Public Methods


    public Tile getTileAt(int x, int y) {
        return this.tiles[y][x];
    }

    public void moveActor(Actor actor, Coordinate newPosition) {
        Coordinate oldPosition = actor.getPosition();

        System.out.println("Moving actor to " + newPosition);
        actor.setPosition(newPosition);

        notifyListeners(actor, oldPosition, newPosition);
    }

    public void addChangeListener(StageListener listener) {
        this.changeListeners.add(listener);
    }


    // -------------------------------------------------------- Private Methods


    private void notifyListeners(Actor actor, Coordinate oldPosition, Coordinate newPosition) {
        for (StageListener listener : changeListeners) {
            listener.onStageChange(actor, oldPosition, newPosition);
        }
    }
}
