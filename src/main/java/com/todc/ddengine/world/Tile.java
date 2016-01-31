package com.todc.ddengine.world;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Tile {


    // ----------------------------------------------------- Instance Variables


    private String name;
    private Glyph glyph;
    private boolean passable;


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


    // ----------------------------------------------------------- Constructors



}
