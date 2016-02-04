package com.todc.ddengine.world;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class TileType {


    // ----------------------------------------------------- Instance Variables


    private String name;
    private Glyph glyph;
    private boolean passable;
    private boolean blocksLight;
    private TileType opensTo;
    private TileType closesTo;


    // --------------------------------------------------------------- Mutators


    public Glyph getGlyph() {
        return glyph;
    }

    public void setGlyph(Glyph glyph) {
        this.glyph = glyph;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public boolean isBlocksLight() {
        return blocksLight;
    }

    public void setBlocksLight(boolean blocksLight) {
        this.blocksLight = blocksLight;
    }

    public TileType closesTo() {
        return closesTo;
    }

    public void setClosesTo(TileType closesTo) {
        this.closesTo = closesTo;
    }

    public TileType opensTo() {
        return opensTo;
    }

    public void setOpensTo(TileType opensTo) {
        this.opensTo = opensTo;
    }


    // ----------------------------------------------------------- Constructors



}
