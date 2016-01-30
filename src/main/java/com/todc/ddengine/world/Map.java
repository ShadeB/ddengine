package com.todc.ddengine.world;


import com.todc.ddengine.world.Tile;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Map {


    // ----------------------------------------------------- Instance Variables


    private Tile[][] tiles;


    // --------------------------------------------------------------- Mutators


    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }


    // ----------------------------------------------------------- Constructors


    public Map(Tile[][] tiles) {
        this.tiles = tiles;
    }
}
