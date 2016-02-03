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
    private Hero hero;

    private int width = 0;
    private int height = 0;

    private List<StageListener> changeListeners = new ArrayList<>();


    // --------------------------------------------------------------- Mutators


    public Tile[][] getTiles() {
        return tiles;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
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


    public Tile getTileAt(Coordinate pos) {
        return this.tiles[pos.y][pos.x];
    }

    public Tile getTileAt(int x, int y) {
        return this.tiles[y][x];
    }

    public void setTileAt(int x, int y, Tile tile) {
        this.tiles[y][x] = tile;
    }

    public void setTileAt(Coordinate pos, Tile tile) {
        this.tiles[pos.y][pos.x] = tile;
    }

    public void moveActor(Actor actor, Coordinate newPosition) {
        Coordinate oldPosition = actor.getPosition();

        System.out.println("Moving actor to " + newPosition);
        actor.setPosition(newPosition);

        notifyListeners(actor);
    }

    public Coordinate findEmptyTile() {
        for (int y=0; y<this.tiles.length; y++) {
            for (int x=0; x<this.tiles[y].length; x++) {
                if (this.tiles[y][x].isPassable()) {
                    return new Coordinate(x, y);
                }
            }
        }

        return null;
    }

    public void setDirty() {
        notifyListeners(null);
    }

    public void addChangeListener(StageListener listener) {
        this.changeListeners.add(listener);
    }


    // -------------------------------------------------------- Private Methods


    private void notifyListeners(Actor actor) {
        for (StageListener listener : changeListeners) {
            listener.onStageChange(actor);
        }
    }
}
