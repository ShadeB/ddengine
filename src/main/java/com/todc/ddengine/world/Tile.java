package com.todc.ddengine.world;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Tile {


    // ----------------------------------------------------- Instance Variables


    private String name;
    private Glyph glyph;
    private boolean passable;
    private Tile opensTo;
    private Tile closesTo;


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

    public Tile closesTo() {
        return closesTo;
    }

    public void setClosesTo(Tile closesTo) {
        this.closesTo = closesTo;
    }

    public Tile opensTo() {
        return opensTo;
    }

    public void setOpensTo(Tile opensTo) {
        this.opensTo = opensTo;
    }


    // ----------------------------------------------------------- Constructors



}
