package com.todc.ddengine.world;

/**
 * @author Tim O'Donnell (tim.odonnell@imperva.com)
 */
public class Tile {


    // ----------------------------------------------------- Instance Variables


    private TileType type;
    private boolean explored = false;
    private boolean visible = false;


    // --------------------------------------------------------------- Mutators


    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        if (visible) explored = true;
        this.visible = visible;
    }


    // ----------------------------------------------------------- Constructors


    public Tile(TileType type) {
        this.type = type;
    }


    // --------------------------------------------------------- Public Methods


    public boolean isPassable() {
        return this.type.isPassable();
    }
}
