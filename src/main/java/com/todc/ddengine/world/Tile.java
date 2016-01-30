package com.todc.ddengine.world;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Tile {


    // ----------------------------------------------------- Instance Variables


    private int terrainType;


    // --------------------------------------------------------------- Mutators


    public int getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(int terrainType) {
        this.terrainType = terrainType;
    }


    // ----------------------------------------------------------- Constructors


    public Tile(int terrainType) {
        this.terrainType = terrainType;
    }
}
